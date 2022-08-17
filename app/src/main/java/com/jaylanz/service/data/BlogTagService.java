package com.jaylanz.service.data;

import com.jaylanz.common.exception.db.EntityNotFoundException;
import com.jaylanz.common.exception.db.InsertFailureException;
import com.jaylanz.domain.dto.TagDTO;
import com.jaylanz.domain.vo.TagVO;

import java.util.List;

public interface BlogTagService {
    List<Long> getTagIdsByBlogId(Long blogId);
    List<TagDTO> getTagsByBlogId(Long blogId);
    void create(Long blogId, List<TagVO> tags) throws EntityNotFoundException, InsertFailureException;
    void delete(Long blogId);
    void delete(List<Long> blogIds);
}
