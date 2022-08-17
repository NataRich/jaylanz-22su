package com.jaylanz.domain.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName("t_token")
public class TokenPO implements Serializable {
    @TableId
    private Long id;
    private Long userId;
    private String ipAddress;
    private String token;
    @Version
    private int version;
    @TableField(fill = FieldFill.INSERT)
    private Timestamp utcCreateTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Timestamp utcUpdateTime;

    public TokenPO() {
    }

    public TokenPO(Long id, Long userId, String ipAddress, String token, int version, Timestamp utcCreateTime, Timestamp utcUpdateTime) {
        this.id = id;
        this.userId = userId;
        this.ipAddress = ipAddress;
        this.token = token;
        this.version = version;
        this.utcCreateTime = utcCreateTime;
        this.utcUpdateTime = utcUpdateTime;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Timestamp getUtcCreateTime() {
        return utcCreateTime;
    }

    public void setUtcCreateTime(Timestamp utcCreateTime) {
        this.utcCreateTime = utcCreateTime;
    }

    public Timestamp getUtcUpdateTime() {
        return utcUpdateTime;
    }

    public void setUtcUpdateTime(Timestamp utcUpdateTime) {
        this.utcUpdateTime = utcUpdateTime;
    }
}
