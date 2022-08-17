package com.jaylanz.domain.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@RedisHash("token")
public class TokenDTO implements Serializable {
    @Id
    private Long id;
    @Indexed
    private Long userId;
    @Indexed
    private String ipAddress;
    private String token;

    public TokenDTO() {
    }

    public TokenDTO(Long id, Long userId, String ipAddress, String token) {
        this.id = id;
        this.userId = userId;
        this.ipAddress = ipAddress;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
