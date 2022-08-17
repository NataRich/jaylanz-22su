package com.jaylanz.domain.dto.repeository;

import com.jaylanz.domain.dto.ProjectTagDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTagRepository extends CrudRepository<ProjectTagDTO, Long> {
    List<ProjectTagDTO> findAllByProjId(Long projId);
}
