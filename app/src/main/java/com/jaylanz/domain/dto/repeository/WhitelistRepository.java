package com.jaylanz.domain.dto.repeository;

import com.jaylanz.domain.dto.WhitelistDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WhitelistRepository extends CrudRepository<WhitelistDTO, Long> {
    Optional<WhitelistDTO> findByIpAddress(String ipAddress);
}
