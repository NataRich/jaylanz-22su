package com.jaylanz.domain.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName("t_user")
public class UserPO implements Serializable {
    @TableId
    private Long id;
    private String username;
    private String password;
    private String emailAddress;
    @Version
    private int version;
    @TableField(fill = FieldFill.INSERT)
    private Timestamp utcCreateTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Timestamp utcUpdateTime;

    public UserPO() {
    }

    public UserPO(Long id, String username, String password, String emailAddress, int version, Timestamp utcCreateTime, Timestamp utcUpdateTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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
