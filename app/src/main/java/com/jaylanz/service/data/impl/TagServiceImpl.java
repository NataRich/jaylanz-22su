package com.jaylanz.service.data.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jaylanz.common.exception.db.EntityNotFoundException;
import com.jaylanz.common.exception.db.InsertFailureException;
import com.jaylanz.common.exception.db.UniqueFieldViolationException;
import com.jaylanz.common.exception.db.UpdateFailureException;
import com.jaylanz.domain.dto.TagDTO;
import com.jaylanz.domain.dto.repeository.TagRepository;
import com.jaylanz.domain.po.TagPO;
import com.jaylanz.domain.po.mapper.TagMapper;
import com.jaylanz.service.data.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private final TagMapper tagMapper;
    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagMapper tagMapper, TagRepository tagRepository) {
        this.tagMapper = tagMapper;
        this.tagRepository = tagRepository;
    }

    @Override
    public TagDTO get(Long id) {
        Optional<TagDTO> tag = tagRepository.findById(id);
        return tag.orElseGet(() -> fetch(id));
    }

    @Override
    public TagDTO get(String tagName) {
        Optional<TagDTO> tag = tagRepository.findByName(tagName);
        return tag.orElseGet(() -> fetch(tagName));
    }

    @Override
    public IPage<TagDTO> get(Long pageNum, Long pageSize) {
        QueryWrapper<TagPO> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("name");
        Page<TagPO> page = tagMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        List<TagDTO> objs = new ArrayList<>();
        page.getRecords().forEach(po -> objs.add(new TagDTO(po)));
        Page<TagDTO> res = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        res.setPages(page.getPages());
        res.setRecords(objs);
        return res;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TagDTO create(String tagName) throws UniqueFieldViolationException, InsertFailureException {
        TagPO po = tagMapper.selectByName(tagName);
        if (po != null)
            throw new UniqueFieldViolationException();

        po = new TagPO();
        po.setName(tagName);
        if (tagMapper.insert(po) > 0) {
            TagDTO dto = new TagDTO(po.getId(), po.getName(), po.getCount());
            tagRepository.save(dto);
            return dto;
        }

        throw new InsertFailureException();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TagDTO update(Long id, String newTagName) throws EntityNotFoundException, UniqueFieldViolationException, UpdateFailureException {
        // id is not found
        TagPO po = tagMapper.selectById(id);
        if (po == null)
            throw new EntityNotFoundException();

        // new name is the same as the old one
        if (po.getName().equals(newTagName))
            return new TagDTO(po.getId(), po.getName(), po.getCount());

        // new name is different from the old one but duplicate with another tag
        if (tagMapper.selectByName(newTagName) != null)
            throw new UniqueFieldViolationException();

        // valid new name
        po.setName(newTagName);
        if (tagMapper.updateById(po) > 0) {
            TagDTO dto = new TagDTO(po.getId(), po.getName(), po.getCount());
            tagRepository.save(dto);
            return dto;
        }

        throw new UpdateFailureException();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void incCount(Long id) {
        TagPO po = tagMapper.selectById(id);
        if (po != null) {
            po.setCount(po.getCount() + 1);
            tagMapper.updateById(po);
            TagDTO dto = new TagDTO(po.getId(), po.getName(), po.getCount());
            tagRepository.save(dto);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void decCount(Long id) {
        TagPO po = tagMapper.selectById(id);
        if (po != null) {
            po.setCount(po.getCount() - 1);
            tagMapper.updateById(po);
            TagDTO dto = new TagDTO(po.getId(), po.getName(), po.getCount());
            tagRepository.save(dto);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {
        tagMapper.deleteById(id);
        tagRepository.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Long> ids) {
        tagMapper.deleteBatchIds(ids);
        tagRepository.deleteAllById(ids);
    }

    private TagDTO fetch(Long id) {
        TagPO po = tagMapper.selectById(id);
        if (po != null) {
            TagDTO dto = new TagDTO(po.getId(), po.getName(), po.getCount());
            tagRepository.save(dto);
            return dto;
        }
        return null;
    }

    private TagDTO fetch(String tagName) {
        TagPO po = tagMapper.selectByName(tagName);
        if (po != null) {
            TagDTO dto = new TagDTO(po.getId(), po.getName(), po.getCount());
            tagRepository.save(dto);
            return dto;
        }
        return null;
    }
}
