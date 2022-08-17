package com.jaylanz.domain.dto.repeository;

import com.jaylanz.domain.dto.BlogTagDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogTagRepository extends CrudRepository<BlogTagDTO, Long> {
    List<BlogTagDTO> findAllByBlogId(Long blogId);
}
