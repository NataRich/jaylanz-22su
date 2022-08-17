package com.jaylanz.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.jaylanz.common.constant.PublishStatus;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName("t_project")
public class ProjectPO implements Serializable {
    @TableId
    private Long id;
    private String title;
    private String description;
    private String repoUrl;
    private String content;
    private boolean complete;
    private Timestamp utcStartTime;
    private Timestamp utcEndTime;
    private PublishStatus status;
    private int views;
    @Version
    private int version;
    @TableField(fill = FieldFill.INSERT)
    private Timestamp utcCreateTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Timestamp utcUpdateTime;

    public ProjectPO() {
    }

    public ProjectPO(Long id, String title, String description, String repoUrl, String content, boolean complete, Timestamp utcStartTime, Timestamp utcEndTime, PublishStatus status, int views, int version, Timestamp utcCreateTime, Timestamp utcUpdateTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.repoUrl = repoUrl;
        this.content = content;
        this.complete = complete;
        this.utcStartTime = utcStartTime;
        this.utcEndTime = utcEndTime;
        this.status = status;
        this.views = views;
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

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public Timestamp getUtcStartTime() {
        return utcStartTime;
    }

    public void setUtcStartTime(Timestamp utcStartTime) {
        this.utcStartTime = utcStartTime;
    }

    public Timestamp getUtcEndTime() {
        return utcEndTime;
    }

    public void setUtcEndTime(Timestamp utcEndTime) {
        this.utcEndTime = utcEndTime;
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
