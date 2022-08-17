package com.jaylanz.domain.po;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName("t_tag")
public class TagPO implements Serializable {
    @TableId
    private Long id;
    private String name;
    private int count;
    @Version
    private int version;
    @TableField(fill = FieldFill.INSERT)
    private Timestamp utcCreateTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Timestamp utcUpdateTime;

    public TagPO() {
    }

    public TagPO(Long id, String name, int count, int version, Timestamp utcCreateTime, Timestamp utcUpdateTime) {
        this.id = id;
        this.name = name;
        this.count = count;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
