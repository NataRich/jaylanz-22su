package com.jaylanz.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("t_whitelist")
public class WhitelistPO implements Serializable {
    @TableId
    private Long id;
    private String ipAddress;

    public WhitelistPO() {
    }

    public WhitelistPO(Long id, String ipAddress) {
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
