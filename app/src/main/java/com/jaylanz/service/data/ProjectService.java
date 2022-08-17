package com.jaylanz.service.data;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jaylanz.common.constant.PublishStatus;
import com.jaylanz.common.exception.db.EntityNotFoundException;
import com.jaylanz.common.exception.db.InsertFailureException;
import com.jaylanz.common.exception.db.UpdateFailureException;
import com.jaylanz.domain.dto.ProjectDTO;
import com.jaylanz.domain.vo.ProjectVO;

import java.util.List;

public interface ProjectService {
    ProjectDTO get(Long id);
    ProjectDTO get(Long id, PublishStatus status);
    List<ProjectDTO> get(String title, PublishStatus status);
    IPage<ProjectDTO> get(Long pageNum, Long pageSize, PublishStatus status);
    ProjectDTO create(ProjectVO vo) throws InsertFailureException;
    ProjectDTO update(ProjectVO vo) throws EntityNotFoundException, UpdateFailureException;
    void incView(Long id);
    void delete(Long id);
    void delete(List<Long> ids);
}
