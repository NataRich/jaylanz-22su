package com.jaylanz.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jaylanz.common.exception.db.EntityNotFoundException;
import com.jaylanz.common.exception.db.InsertFailureException;
import com.jaylanz.common.exception.db.UniqueFieldViolationException;
import com.jaylanz.common.exception.db.UpdateFailureException;
import com.jaylanz.common.validation.group.OnTagCreate;
import com.jaylanz.common.validation.group.OnTagUpdate;
import com.jaylanz.domain.dto.TagDTO;
import com.jaylanz.domain.vo.*;
import com.jaylanz.service.data.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/resource/tag")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Object>> getOne(@PathVariable("id") Long id) {
        TagDTO dto = tagService.get(id);
        if (dto != null) {
            TagVO vo = new TagVO(dto.getId(), dto.getName(), dto.getCount());
            return new ResponseEntity<>(BaseResponse.OK(vo), HttpStatus.OK);
        }
        return new ResponseEntity<>(BaseResponse.NOT_FOUND(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<Object>> getPage(@Valid PagingRequest pagingRequest) {
        IPage<TagDTO> p = tagService.get(pagingRequest.getPageNum(), pagingRequest.getPageSize());
        ResourcePage<TagVO> vo = new ResourcePage<>(p.getCurrent(), p.getSize(), p.getTotal(), p.getPages(), TagVO.transform(p.getRecords()));
        return new ResponseEntity<>(BaseResponse.OK(vo), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Object>> create(@Validated(OnTagCreate.class) @RequestBody TagVO vo) {
        try {
            TagDTO dto = tagService.create(vo.getName());
            vo = new TagVO(dto.getId(), dto.getName(), dto.getCount());
            return new ResponseEntity<>(BaseResponse.OK(vo), HttpStatus.OK);
        } catch (UniqueFieldViolationException e) {
            return new ResponseEntity<>(BaseResponse.DUPLICATE(), HttpStatus.CONFLICT);
        } catch (InsertFailureException e) {
            return new ResponseEntity<>(BaseResponse.ERROR(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<BaseResponse<Object>> update(@Validated(OnTagUpdate.class) @RequestBody TagVO tagVO) {
        try {
            TagDTO dto = tagService.update(tagVO.getId(), tagVO.getName());
            TagVO vo = new TagVO(dto.getId(), dto.getName(), dto.getCount());
            return new ResponseEntity<>(BaseResponse.OK(vo), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(BaseResponse.NOT_FOUND(), HttpStatus.BAD_REQUEST);
        } catch (UniqueFieldViolationException e) {
            return new ResponseEntity<>(BaseResponse.DUPLICATE(), HttpStatus.CONFLICT);
        } catch (UpdateFailureException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(BaseResponse.ERROR(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Object>> delete(@PathVariable("id") Long id) {
        try {
            tagService.delete(id);
            return new ResponseEntity<>(BaseResponse.OK(), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(BaseResponse.NOT_FOUND(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<Object>> delete(@RequestParam("ids") List<Long> ids) {
        tagService.delete(ids);
        return new ResponseEntity<>(BaseResponse.OK(), HttpStatus.OK);
    }
}
