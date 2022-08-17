package com.jaylanz.service.data.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jaylanz.common.exception.db.EntityNotFoundException;
import com.jaylanz.common.exception.db.InsertFailureException;
import com.jaylanz.domain.dto.ProjectTagDTO;
import com.jaylanz.domain.dto.TagDTO;
import com.jaylanz.domain.dto.repeository.ProjectTagRepository;
import com.jaylanz.domain.po.ProjectTagPO;
import com.jaylanz.domain.po.mapper.ProjectTagMapper;
import com.jaylanz.domain.vo.TagVO;
import com.jaylanz.service.data.ProjectTagService;
import com.jaylanz.service.data.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProjectTagServiceImpl implements ProjectTagService {
    private final ProjectTagMapper projectTagMapper;
    private final ProjectTagRepository projectTagRepository;
    private final TagService tagService;

    @Autowired
    public ProjectTagServiceImpl(ProjectTagMapper projectTagMapper, ProjectTagRepository projectTagRepository, TagService tagService) {
        this.projectTagMapper = projectTagMapper;
        this.projectTagRepository = projectTagRepository;
        this.tagService = tagService;
    }

    @Override
    public List<Long> getTagIdsByProjId(Long projId) {
        List<ProjectTagDTO> dtos = projectTagRepository.findAllByProjId(projId);
        if (dtos.isEmpty() || dtos.get(0).getCount() != dtos.size()) {
            dtos = new ArrayList<>();
            List<ProjectTagPO> pos = projectTagMapper.selectAllByProjId(projId);
            for (ProjectTagPO po : pos) {
                ProjectTagDTO dto = new ProjectTagDTO(po);
                projectTagRepository.save(dto);
                dtos.add(dto);
            }
        }

        List<Long> ids = new ArrayList<>();
        for (ProjectTagDTO dto : dtos)
            ids.add(dto.getTagId());
        return ids;
    }

    @Override
    public List<TagDTO> getTagsByProjId(Long projId) {
        List<Long> tagIds = getTagIdsByProjId(projId);
        List<TagDTO> tags = new ArrayList<>();
        tagIds.forEach(id -> tags.add(tagService.get(id)));
        return tags;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(Long projId, List<TagVO> tags) throws EntityNotFoundException, InsertFailureException {
        List<Long> tagIds = new ArrayList<>();
        tags.forEach(tag -> tagIds.add(tag.getId()));

        Set<Long> idSet = new HashSet<>(tagIds);
        for (Long tagId : idSet)
            if (tagService.get(tagId) == null)
                throw new EntityNotFoundException();

        for (Long tagId : tagIds) {
            ProjectTagPO po = new ProjectTagPO();
            po.setProjId(projId);
            po.setTagId(tagId);
            po.setCount(tags.size());
            if (projectTagMapper.insert(po) > 0) {
                projectTagRepository.save(new ProjectTagDTO(po));
                tagService.incCount(tagId);
            } else {
                throw new InsertFailureException();
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long projId) {
        List<Long> ids = getTagIdsByProjId(projId);
        ids.forEach(tagService::decCount);
        projectTagMapper.deleteByProjId(projId);
        projectTagRepository.deleteAll(projectTagRepository.findAllByProjId(projId));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Long> projIds) {
        QueryWrapper<ProjectTagPO> wrapper = new QueryWrapper<>();
        wrapper.in("proj_id", projIds);
        projectTagMapper.delete(wrapper);
        for (Long projId : projIds)
            projectTagRepository.deleteAll(projectTagRepository.findAllByProjId(projId));
    }
}
