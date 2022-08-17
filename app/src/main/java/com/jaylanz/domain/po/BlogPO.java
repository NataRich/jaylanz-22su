package com.jaylanz.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.jaylanz.common.constant.PublishStatus;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName("t_blog")
public class BlogPO implements Serializable {
    @TableId
    private Long id;
    private String title;
    private String description;
    private int readTime;
    private String content;
    private PublishStatus status;
    private int views;
    private Timestamp utcPublishTime;
    @Version
    private int version;
    @TableField(fill = FieldFill.INSERT)
    private Timestamp utcCreateTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Timestamp utcUpdateTime;

    public BlogPO() {
    }

    public BlogPO(Long id, String title, String description, int readTime, String content, PublishStatus status, int views, Timestamp utcPublishTime, int version, Timestamp utcCreateTime, Timestamp utcUpdateTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.readTime = readTime;
        this.content = content;
        this.status = status;
        this.views = views;
        this.utcPublishTime = utcPublishTime;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReadTime() {
        return readTime;
    }

    public void setReadTime(int readTime) {
        this.readTime = readTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PublishStatus getStatus() {
        return status;
    }

    public void setStatus(PublishStatus status) {
        this.status = status;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Timestamp getUtcPublishTime() {
        return utcPublishTime;
    }

    public void setUtcPublishTime(Timestamp utcPublishTime) {
        this.utcPublishTime = utcPublishTime;
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
