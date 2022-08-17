package com.jaylanz.domain.dto.repeository;

import com.jaylanz.domain.dto.TagDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends CrudRepository<TagDTO, Long> {
    Optional<TagDTO> findByName(String tagName);
}
