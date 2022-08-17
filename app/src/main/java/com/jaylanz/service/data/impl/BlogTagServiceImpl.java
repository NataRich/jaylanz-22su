package com.jaylanz.service.data.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jaylanz.common.exception.db.EntityNotFoundException;
import com.jaylanz.common.exception.db.InsertFailureException;
import com.jaylanz.domain.dto.BlogTagDTO;
import com.jaylanz.domain.dto.TagDTO;
import com.jaylanz.domain.dto.repeository.BlogTagRepository;
import com.jaylanz.domain.po.BlogTagPO;
import com.jaylanz.domain.po.mapper.BlogTagMapper;
import com.jaylanz.domain.vo.TagVO;
import com.jaylanz.service.data.BlogTagService;
import com.jaylanz.service.data.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BlogTagServiceImpl implements BlogTagService {
    private final BlogTagMapper blogTagMapper;
    private final BlogTagRepository blogTagRepository;
    private final TagService tagService;

    @Autowired
    public BlogTagServiceImpl(BlogTagMapper blogTagMapper, BlogTagRepository blogTagRepository, TagService tagService) {
        this.blogTagMapper = blogTagMapper;
        this.blogTagRepository = blogTagRepository;
        this.tagService = tagService;
    }

    @Override
    public List<Long> getTagIdsByBlogId(Long blogId) {
        List<BlogTagDTO> dtos = blogTagRepository.findAllByBlogId(blogId);
        if (dtos.isEmpty() || dtos.get(0).getCount() != dtos.size()) {
            dtos = new ArrayList<>();
            List<BlogTagPO> pos = blogTagMapper.selectAllByBlogId(blogId);
            for (BlogTagPO po : pos) {
                BlogTagDTO dto = new BlogTagDTO(po);
                blogTagRepository.save(dto);
                dtos.add(dto);
            }
        }

        List<Long> ids = new ArrayList<>();
        for (BlogTagDTO dto : dtos)
            ids.add(dto.getTagId());
        return ids;
    }

    @Override
    public List<TagDTO> getTagsByBlogId(Long blogId) {
        List<Long> tagIds = getTagIdsByBlogId(blogId);
        List<TagDTO> tags = new ArrayList<>();
        tagIds.forEach(id -> tags.add(tagService.get(id)));
        return tags;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(Long blogId, List<TagVO> tags) throws EntityNotFoundException, InsertFailureException {
        List<Long> tagIds = new ArrayList<>();
        tags.forEach(tag -> tagIds.add(tag.getId()));

        Set<Long> idSet = new HashSet<>(tagIds);
        for (Long tagId : idSet)
            if (tagService.get(tagId) == null)
                throw new EntityNotFoundException();

        for (Long tagId : tagIds) {
            BlogTagPO po = new BlogTagPO();
            po.setBlogId(blogId);
            po.setTagId(tagId);
            po.setCount(tags.size());
            if (blogTagMapper.insert(po) > 0 ) {
                blogTagRepository.save(new BlogTagDTO(po));
                tagService.incCount(tagId);
            } else {
                throw new InsertFailureException();
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long blogId) {
        List<Long> ids = getTagIdsByBlogId(blogId);
        ids.forEach(tagService::decCount);
        blogTagMapper.deleteByBlogId(blogId);
        blogTagRepository.deleteAll(blogTagRepository.findAllByBlogId(blogId));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Long> blogIds) {
        QueryWrapper<BlogTagPO> wrapper = new QueryWrapper<>();
        wrapper.in("blog_id", blogIds);
        blogTagMapper.delete(wrapper);
        for (Long blogId : blogIds)
            blogTagRepository.deleteAll(blogTagRepository.findAllByBlogId(blogId));
    }
}
