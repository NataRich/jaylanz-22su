package com.jaylanz.service.data;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jaylanz.common.exception.db.EntityNotFoundException;
import com.jaylanz.common.exception.db.InsertFailureException;
import com.jaylanz.common.exception.db.UniqueFieldViolationException;
import com.jaylanz.common.exception.db.UpdateFailureException;
import com.jaylanz.domain.dto.TagDTO;

import java.util.List;

public interface TagService {
    TagDTO get(Long id);
    TagDTO get(String tagName);
    IPage<TagDTO> get(Long pageNum, Long pageSize);
    TagDTO create(String tagName) throws UniqueFieldViolationException, InsertFailureException;
    TagDTO update(Long id, String newTagName) throws EntityNotFoundException, UniqueFieldViolationException, UpdateFailureException;
    void incCount(Long id);
    void decCount(Long id);
    void delete(Long id);
    void delete(List<Long> ids);
}
