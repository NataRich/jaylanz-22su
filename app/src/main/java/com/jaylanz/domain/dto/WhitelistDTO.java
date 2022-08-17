package com.jaylanz.domain.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@RedisHash("whitelist")
public class WhitelistDTO implements Serializable {
    @Id
    private Long id;
    @Indexed
    private String ipAddress;

    public WhitelistDTO() {
    }

    public WhitelistDTO(Long id, String ipAddress) {
        this.id = id;
        this.ipAddress = ipAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
