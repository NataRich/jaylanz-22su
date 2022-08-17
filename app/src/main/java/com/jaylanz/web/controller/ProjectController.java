package com.jaylanz.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jaylanz.common.constant.PublishStatus;
import com.jaylanz.common.exception.db.EntityNotFoundException;
import com.jaylanz.common.exception.db.InsertFailureException;
import com.jaylanz.common.exception.db.UpdateFailureException;
import com.jaylanz.common.validation.group.OnProjectCreate;
import com.jaylanz.common.validation.group.OnProjectUpdate;
import com.jaylanz.domain.dto.ProjectDTO;
import com.jaylanz.domain.dto.TagDTO;
import com.jaylanz.domain.vo.*;
import com.jaylanz.service.data.ProjectService;
import com.jaylanz.service.data.ProjectTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/v1/resource/project")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectTagService projectTagService;

    @Autowired
    public ProjectController(ProjectService projectService, ProjectTagService projectTagService) {
        this.projectService = projectService;
        this.projectTagService = projectTagService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Object>> getOne(@PathVariable("id") Long id) {
        ProjectDTO dto = projectService.get(id, PublishStatus.PUBLISHED);

        // id doesn't exist
        if (dto == null)
            return new ResponseEntity<>(BaseResponse.OK(), HttpStatus.OK);

        // found draft projects (only admins can see)
        if (dto.getStatus() == PublishStatus.DRAFT && SecurityContextHolder.getContext().getAuthentication() == null)
            return new ResponseEntity<>(BaseResponse.OK(), HttpStatus.OK);

        List<TagDTO> tags = projectTagService.getTagsByProjId(id);
        return new ResponseEntity<>(BaseResponse.OK(new ProjectVO(dto, tags)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<Object>> getPage(@Valid PagingRequest pagingRequest, @RequestParam(value = "status", required = false) PublishStatus status) {
        // guests are forbidden to view drafts
        if (SecurityContextHolder.getContext().getAuthentication() == null)
            if (status == null || status == PublishStatus.DRAFT)
                return new ResponseEntity<>(BaseResponse.OK(), HttpStatus.OK);

        IPage<ProjectDTO> page = projectService.get(pagingRequest.getPageNum(), pagingRequest.getPageSize(), status);
        List<ProjectVO> vos = new ArrayList<>();
        page.getRecords().forEach(dto -> vos.add(new ProjectVO(dto, projectTagService.getTagsByProjId(dto.getId()))));
        ResourcePage<ProjectVO> resource = new ResourcePage<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getPages(), vos);
        return new ResponseEntity<>(BaseResponse.OK(resource), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Object>> create(@Validated(OnProjectCreate.class) @RequestBody ProjectVO vo) {
        ProjectDTO dto;
        try {
            dto = projectService.create(vo);
            projectTagService.create(dto.getId(), vo.getTags());
            List<TagDTO> tags = projectTagService.getTagsByProjId(dto.getId());
            return new ResponseEntity<>(BaseResponse.OK(new ProjectVO(dto, tags)), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(BaseResponse.NOT_FOUND(), HttpStatus.BAD_REQUEST);
        } catch (InsertFailureException e) {
            return new ResponseEntity<>(BaseResponse.ERROR(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // TODO: log unexpected error
            System.out.println(e.getMessage());
            return new ResponseEntity<>(BaseResponse.ERROR(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<BaseResponse<Object>> update(@Validated(OnProjectUpdate.class) @RequestBody ProjectVO vo) {
        try {
            ProjectDTO dto = projectService.update(vo);
            projectTagService.delete(dto.getId());
            projectTagService.create(dto.getId(), vo.getTags());
            List<TagDTO> tags = projectTagService.getTagsByProjId(dto.getId());
            return new ResponseEntity<>(BaseResponse.OK(new ProjectVO(dto, tags)), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(BaseResponse.NOT_FOUND(), HttpStatus.BAD_REQUEST);
        } catch (UpdateFailureException | InsertFailureException e) {
            return new ResponseEntity<>(BaseResponse.ERROR(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // TODO: log unexpected error
            System.out.println(Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(BaseResponse.ERROR(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Object>> delete(@PathVariable("id") Long id) {
        projectService.delete(id);
        projectTagService.delete(id);
        return new ResponseEntity<>(BaseResponse.OK(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<Object>> delete(@RequestParam("ids") List<Long> ids) {
        projectService.delete(ids);
        projectTagService.delete(ids);
        return new ResponseEntity<>(BaseResponse.OK(), HttpStatus.OK);
    }
}
