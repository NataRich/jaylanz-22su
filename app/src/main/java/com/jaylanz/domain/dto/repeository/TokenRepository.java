package com.jaylanz.domain.dto.repeository;

import com.jaylanz.domain.dto.TokenDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends CrudRepository<TokenDTO, Long> {
    List<TokenDTO> findAllByUserIdAndIpAddress(Long userId, String ipAddress);
}
