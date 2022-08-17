package com.jaylanz.service.data;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jaylanz.common.exception.db.EntityNotFoundException;
import com.jaylanz.common.exception.db.InsertFailureException;
import com.jaylanz.common.exception.db.UpdateFailureException;
import com.jaylanz.domain.dto.BlogDTO;
import com.jaylanz.domain.vo.BlogVO;

import java.util.List;

public interface BlogService {
    BlogDTO get(Long id);
    IPage<BlogDTO> get(Long pageNum, Long pageSize);
    BlogDTO create(BlogVO vo) throws InsertFailureException;
    BlogDTO update(BlogVO vo) throws EntityNotFoundException, UpdateFailureException;
    void incView(Long id);
    void delete(Long id);
    void delete(List<Long> ids);
}
