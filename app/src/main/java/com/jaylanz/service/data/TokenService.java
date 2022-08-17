package com.jaylanz.service.data;

import com.jaylanz.domain.dto.TokenDTO;

public interface TokenService {
    TokenDTO get(Long userId, String ipAddress);
    boolean isExpired(Long userId, String ipAddress);
    boolean isExpired(String token);
    TokenDTO create(Long userId, String username, String ipAddress);
    TokenDTO update(Long userId, String username, String ipAddress);
    void delete(Long tokenId);

    void scheduledClear();
}
