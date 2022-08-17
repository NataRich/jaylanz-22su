package com.jaylanz.service.data.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jaylanz.common.constant.PublishStatus;
import com.jaylanz.common.exception.db.EntityNotFoundException;
import com.jaylanz.common.exception.db.InsertFailureException;
import com.jaylanz.common.exception.db.UpdateFailureException;
import com.jaylanz.domain.dto.ProjectDTO;
import com.jaylanz.domain.dto.repeository.ProjectRepository;
import com.jaylanz.domain.po.ProjectPO;
import com.jaylanz.domain.po.mapper.ProjectMapper;
import com.jaylanz.domain.vo.ProjectVO;
import com.jaylanz.service.data.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@EnableAsync
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectMapper projectMapper, ProjectRepository projectRepository) {
        this.projectMapper = projectMapper;
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectDTO get(Long id) {
        Optional<ProjectDTO> proj = projectRepository.findById(id);
        return proj.orElseGet(() -> fetch(id, null));
    }

    @Override
    public ProjectDTO get(Long id, PublishStatus status) {
        Optional<ProjectDTO> proj = projectRepository.findByIdAndStatus(id, status);
        return proj.orElseGet(() -> fetch(id, status));
    }

    @Override
    public List<ProjectDTO> get(String title, PublishStatus status) {
        QueryWrapper<ProjectPO> wrapper = new QueryWrapper<>();
        if (status != null)
            wrapper.eq("status", status);
        wrapper.like("title", title);  // utf8mb4_0900_ai_ci
        List<ProjectPO> pos = projectMapper.selectList(wrapper);
        List<ProjectDTO> dtos = new ArrayList<>();
        pos.forEach(po -> dtos.add(new ProjectDTO(po)));
        return dtos;
    }

    @Override
    public IPage<ProjectDTO> get(Long pageNum, Long pageSize, PublishStatus status) {
        QueryWrapper<ProjectPO> wrapper = new QueryWrapper<>();
        if (status != null)
            wrapper.eq("status", status);
        wrapper.orderByAsc("complete").orderByDesc("utc_start_time").orderByDesc("utc_create_time");
        Page<ProjectPO> page = projectMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        List<ProjectDTO> objs = new ArrayList<>();
        page.getRecords().forEach(po -> objs.add(new ProjectDTO(po)));
        Page<ProjectDTO> res = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        res.setPages(page.getPages());
        res.setRecords(objs);
        return res;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProjectDTO create(ProjectVO vo) throws InsertFailureException {
        ProjectPO po = new ProjectPO();
        po.setTitle(vo.getTitle());
        po.setDescription(vo.getDescription());
        po.setRepoUrl(vo.getRepoUrl());
        po.setContent(vo.getContent());
        po.setComplete(vo.isComplete());
        po.setUtcStartTime(vo.getUtcStartTime());
        po.setUtcEndTime(vo.getUtcEndTime());
        po.setStatus(vo.getStatus());
        po.setViews(0);
        if (projectMapper.insert(po) > 0) {
            ProjectDTO dto = new ProjectDTO(po);
            projectRepository.save(dto);
            return dto;
        }

        throw new InsertFailureException();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProjectDTO update(ProjectVO vo) throws EntityNotFoundException, UpdateFailureException {
        ProjectPO po = projectMapper.selectById(vo.getId());
        if (po == null)
            throw new EntityNotFoundException();

        po.setTitle(vo.getTitle());
        po.setDescription(vo.getDescription());
        po.setRepoUrl(vo.getRepoUrl());
        po.setContent(vo.getContent());
        po.setComplete(vo.isComplete());
        po.setUtcStartTime(vo.getUtcStartTime());
        po.setUtcEndTime(vo.getUtcEndTime());
        po.setStatus(vo.getStatus());
        if (projectMapper.updateById(po) > 0) {
            ProjectDTO dto = new ProjectDTO(po);
            projectRepository.save(dto);
            return dto;
        }

        throw new UpdateFailureException();
    }

    @Transactional(rollbackFor = Exception.class)
    @Async
    @Override
    public void incView(Long id) {
        ProjectPO po = projectMapper.selectById(id);
        if (po != null) {
            po.setViews(po.getViews() + 1);
            projectMapper.updateById(po);
            projectRepository.save(new ProjectDTO(po));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {
        projectMapper.deleteById(id);
        projectRepository.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Long> ids) {
        projectMapper.deleteBatchIds(ids);
        projectRepository.deleteAllById(ids);
    }

    private ProjectDTO fetch(Long id, PublishStatus status) {
        QueryWrapper<ProjectPO> wrapper = new QueryWrapper<>();
        if (status != null)
            wrapper.eq("status", status);
        wrapper.eq("id", id);
        ProjectPO po = projectMapper.selectOne(wrapper);
        if (po != null) {
            ProjectDTO dto = new ProjectDTO(po);
            projectRepository.save(dto);
            return dto;
        }
        return null;
    }
}
