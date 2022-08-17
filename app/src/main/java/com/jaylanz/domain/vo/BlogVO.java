package com.jaylanz.domain.vo;

import com.jaylanz.common.constant.PublishStatus;
import com.jaylanz.common.validation.group.OnBlogCreate;
import com.jaylanz.common.validation.group.OnBlogUpdate;
import com.jaylanz.domain.dto.BlogDTO;
import com.jaylanz.domain.dto.TagDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class BlogVO implements Serializable {
    @NotNull(groups = OnBlogUpdate.class)
    private Long id;
    @NotEmpty(groups = {OnBlogCreate.class, OnBlogUpdate.class})
    private String title;
    @NotEmpty(groups = {OnBlogCreate.class, OnBlogUpdate.class})
    private String description;
    private int readTime;
    @NotEmpty(groups = {OnBlogCreate.class, OnBlogUpdate.class})
    private String content;
    @NotNull(groups = {OnBlogCreate.class, OnBlogUpdate.class})
    private PublishStatus status;
    private int views;
    private Timestamp utcPublishTime;
    @Valid
    @NotNull(groups = {OnBlogCreate.class, OnBlogUpdate.class})
    private List<TagVO> tags;

    public BlogVO() {
    }

    public BlogVO(BlogDTO dto, List<TagDTO> tags) {
        this(dto.getId(), dto.getTitle(), dto.getDescription(), dto.getReadTime(), dto.getContent(), dto.getStatus(),
                dto.getViews(), dto.getUtcPublishTime(), TagVO.transform(tags));
    }

    public BlogVO(Long id, String title, String description, int readTime, String content, PublishStatus status, int views, Timestamp utcPublishTime, List<TagVO> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.readTime = readTime;
        this.content = content;
        this.status = status;
        this.views = views;
        this.utcPublishTime = utcPublishTime;
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

    public List<TagVO> getTags() {
        return tags;
    }

    public void setTags(List<TagVO> tags) {
        this.tags = tags;
    }
}
