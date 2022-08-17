package com.jaylanz.domain.dto.repeository;

import com.jaylanz.domain.dto.UserDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserDTO, Long> {
    Optional<UserDTO> findByUsername(String username);
}
