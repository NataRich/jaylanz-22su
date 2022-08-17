package com.jaylanz.domain.dto.repeository;

import com.jaylanz.domain.dto.BlogDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends CrudRepository<BlogDTO, Long> {
}
