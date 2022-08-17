package com.jaylanz.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jaylanz.common.exception.db.EntityNotFoundException;
import com.jaylanz.common.exception.db.InsertFailureException;
import com.jaylanz.common.exception.db.UpdateFailureException;
import com.jaylanz.common.validation.group.OnBlogCreate;
import com.jaylanz.common.validation.group.OnBlogUpdate;
import com.jaylanz.domain.dto.BlogDTO;
import com.jaylanz.domain.dto.TagDTO;
import com.jaylanz.domain.vo.BaseResponse;
import com.jaylanz.domain.vo.BlogVO;
import com.jaylanz.domain.vo.PagingRequest;
import com.jaylanz.domain.vo.ResourcePage;
import com.jaylanz.service.data.BlogService;
import com.jaylanz.service.data.BlogTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/resource/blog")
public class BlogController {
    private final BlogService blogService;
    private final BlogTagService blogTagService;

    @Autowired
    public BlogController(BlogService blogService, BlogTagService blogTagService) {
        this.blogService = blogService;
        this.blogTagService = blogTagService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Object>> getOne(@PathVariable("id") Long id) {
        BlogDTO dto = blogService.get(id);
        List<TagDTO> tags = blogTagService.getTagsByBlogId(id);
        return new ResponseEntity<>(BaseResponse.OK(new BlogVO(dto, tags)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<Object>> getPage(@Valid PagingRequest pagingRequest) {
        IPage<BlogDTO> page = blogService.get(pagingRequest.getPageNum(), pagingRequest.getPageSize());
        List<BlogVO> vos = new ArrayList<>();
        for (BlogDTO dto : page.getRecords())
            vos.add(new BlogVO(dto, blogTagService.getTagsByBlogId(dto.getId())));
        ResourcePage<BlogVO> resource = new ResourcePage<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getPages(), vos);
        return new ResponseEntity<>(BaseResponse.OK(resource), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Object>> create(@Validated(OnBlogCreate.class) @RequestBody BlogVO vo) {
        BlogDTO dto;
        try {
            dto = blogService.create(vo);
            blogTagService.create(dto.getId(), vo.getTags());
            List<TagDTO> tags = blogTagService.getTagsByBlogId(dto.getId());
            return new ResponseEntity<>(BaseResponse.OK(new BlogVO(dto, tags)), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(BaseResponse.NOT_FOUND(), HttpStatus.BAD_REQUEST);
        } catch (InsertFailureException e) {
            return new ResponseEntity<>(BaseResponse.ERROR(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // TODO: log unexpected error
            System.out.println("Unexpected error in blog create");
            return new ResponseEntity<>(BaseResponse.ERROR(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<BaseResponse<Object>> update(@Validated(OnBlogUpdate.class) @RequestBody BlogVO vo) {
        try {
            BlogDTO dto = blogService.update(vo);
            blogTagService.delete(dto.getId());
            blogTagService.create(dto.getId(), vo.getTags());
            List<TagDTO> tags = blogTagService.getTagsByBlogId(dto.getId());
            return new ResponseEntity<>(BaseResponse.OK(new BlogVO(dto, tags)), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(BaseResponse.NOT_FOUND(), HttpStatus.BAD_REQUEST);
        } catch (UpdateFailureException e) {
            return new ResponseEntity<>(BaseResponse.ERROR(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // TODO: log unexpected error
            System.out.println("Unexpected error in blog create");
            return new ResponseEntity<>(BaseResponse.ERROR(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Object>> delete(@PathVariable("id") Long id) {
        blogService.delete(id);
        blogTagService.delete(id);
        return new ResponseEntity<>(BaseResponse.OK(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<Object>> delete(@RequestParam("ids") List<Long> ids) {
        blogService.delete(ids);
        blogTagService.delete(ids);
        return new ResponseEntity<>(BaseResponse.OK(), HttpStatus.OK);
    }
}
