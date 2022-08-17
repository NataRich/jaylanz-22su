package com.jaylanz.domain.vo;

import com.jaylanz.common.constant.PublishStatus;
import com.jaylanz.common.validation.group.OnProjectCreate;
import com.jaylanz.common.validation.group.OnProjectUpdate;
import com.jaylanz.domain.dto.ProjectDTO;
import com.jaylanz.domain.dto.TagDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class ProjectVO implements Serializable {
    @NotNull(groups = OnProjectUpdate.class)
    private Long id;
    @NotEmpty(groups = {OnProjectCreate.class, OnProjectUpdate.class})
    private String title;
    @NotEmpty(groups = {OnProjectCreate.class, OnProjectUpdate.class})
    private String description;
    @NotEmpty(groups = {OnProjectCreate.class, OnProjectUpdate.class})
    private String repoUrl;
    @NotEmpty(groups = {OnProjectCreate.class, OnProjectUpdate.class})
    private String content;
    private boolean complete;
    @NotNull(groups = {OnProjectCreate.class, OnProjectUpdate.class})
    private Timestamp utcStartTime;
    private Timestamp utcEndTime;
    @NotNull(groups = {OnProjectCreate.class, OnProjectUpdate.class})
    private PublishStatus status;
    private int views;
    @Valid
    @NotNull(groups = {OnProjectCreate.class, OnProjectUpdate.class})
    private List<TagVO> tags;

    public ProjectVO() {
    }

    public ProjectVO(ProjectDTO dto, List<TagDTO> tags) {
        this(dto.getId(), dto.getTitle(), dto.getDescription(), dto.getRepoUrl(), dto.getContent(), dto.isComplete(),
                dto.getUtcStartTime(), dto.getUtcEndTime(), dto.getStatus(), dto.getViews(), TagVO.transform(tags));
    }

    public ProjectVO(Long id, String title, String description, String repoUrl, String content, boolean complete, Timestamp utcStartTime, Timestamp utcEndTime, PublishStatus status, int views, List<TagVO> tags) {
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
        this.tags = tags;
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

    public List<TagVO> getTags() {
        return tags;
    }

    public void setTags(List<TagVO> tags) {
        this.tags = tags;
    }
}
