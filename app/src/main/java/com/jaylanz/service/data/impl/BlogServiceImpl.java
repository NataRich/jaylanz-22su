package com.jaylanz.service.data.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jaylanz.common.constant.PublishStatus;
import com.jaylanz.common.exception.db.EntityNotFoundException;
import com.jaylanz.common.exception.db.InsertFailureException;
import com.jaylanz.common.exception.db.UpdateFailureException;
import com.jaylanz.domain.dto.BlogDTO;
import com.jaylanz.domain.dto.repeository.BlogRepository;
import com.jaylanz.domain.po.BlogPO;
import com.jaylanz.domain.po.mapper.BlogMapper;
import com.jaylanz.domain.vo.BlogVO;
import com.jaylanz.service.data.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@EnableAsync
@Service
public class BlogServiceImpl implements BlogService {
    private final BlogMapper blogMapper;
    private final BlogRepository blogRepository;

    @Autowired
    public BlogServiceImpl(BlogMapper blogMapper, BlogRepository blogRepository) {
        this.blogMapper = blogMapper;
        this.blogRepository = blogRepository;
    }

    @Override
    public BlogDTO get(Long id) {
        Optional<BlogDTO> blog = blogRepository.findById(id);
        return blog.orElseGet(() -> fetch(id));
    }

    @Override
    public IPage<BlogDTO> get(Long pageNum, Long pageSize) {
        QueryWrapper<BlogPO> wrapper = new QueryWrapper<>();
        wrapper.eq("status", PublishStatus.PUBLISHED).isNotNull("utc_publish_time");
        wrapper.orderByDesc("utc_publish_time").orderByDesc("utc_create_time");
        Page<BlogPO> page = blogMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        List<BlogDTO> objs = new ArrayList<>();
        page.getRecords().forEach(po -> objs.add(new BlogDTO(po)));
        Page<BlogDTO> res = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        res.setPages(page.getPages());
        res.setRecords(objs);
        return res;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BlogDTO create(BlogVO vo) throws InsertFailureException {
        BlogPO po = new BlogPO();
        po.setTitle(vo.getTitle());
        po.setDescription(vo.getDescription());
        po.setReadTime(vo.getReadTime());
        po.setContent(vo.getContent());
        po.setStatus(vo.getStatus());
        po.setViews(0);
        po.setUtcPublishTime(vo.getUtcPublishTime());
        if (blogMapper.insert(po) > 0) {
            BlogDTO dto = new BlogDTO(po);
            blogRepository.save(dto);
            return dto;
        }

        throw new InsertFailureException();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BlogDTO update(BlogVO vo) throws EntityNotFoundException, UpdateFailureException {
        BlogPO po = blogMapper.selectById(vo.getId());
        if (po == null)
            throw new EntityNotFoundException();

        po.setTitle(vo.getTitle());
        po.setDescription(vo.getDescription());
        po.setReadTime(vo.getReadTime());
        po.setContent(vo.getContent());
        po.setStatus(vo.getStatus());
        po.setUtcPublishTime(vo.getUtcPublishTime());
        if (blogMapper.updateById(po) > 0) {
            BlogDTO dto = new BlogDTO(po);
            blogRepository.save(dto);
            return dto;
        }

        throw new UpdateFailureException();
    }

    @Transactional(rollbackFor = Exception.class)
    @Async
    @Override
    public void incView(Long id) {
        BlogPO po = blogMapper.selectById(id);
        if (po != null) {
            po.setViews(po.getViews() + 1);
            blogMapper.updateById(po);
            blogRepository.save(new BlogDTO(po));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {
        blogMapper.deleteById(id);
        blogRepository.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Long> ids) {
        blogMapper.deleteBatchIds(ids);
        blogRepository.deleteAllById(ids);
    }

    private BlogDTO fetch(Long id) {
        BlogPO po = blogMapper.selectById(id);
        if (po != null) {
            BlogDTO dto = new BlogDTO(po);
            blogRepository.save(dto);
            return dto;
        }
        return null;
    }
}
