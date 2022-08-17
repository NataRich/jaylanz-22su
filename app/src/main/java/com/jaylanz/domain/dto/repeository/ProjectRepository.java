package com.jaylanz.domain.dto.repeository;

import com.jaylanz.common.constant.PublishStatus;
import com.jaylanz.domain.dto.ProjectDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends CrudRepository<ProjectDTO, Long> {
    Optional<ProjectDTO> findByIdAndStatus(Long id, PublishStatus status);
}
