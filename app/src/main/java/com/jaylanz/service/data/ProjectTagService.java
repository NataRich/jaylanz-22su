package com.jaylanz.service.data;

import com.jaylanz.common.exception.db.EntityNotFoundException;
import com.jaylanz.common.exception.db.InsertFailureException;
import com.jaylanz.domain.dto.TagDTO;
import com.jaylanz.domain.vo.TagVO;

import java.util.List;

public interface ProjectTagService {
    List<Long> getTagIdsByProjId(Long projId);
    List<TagDTO> getTagsByProjId(Long projId);
    void create(Long projId, List<TagVO> tags) throws EntityNotFoundException, InsertFailureException;
    void delete(Long projId);
    void delete(List<Long> projIds);
}
