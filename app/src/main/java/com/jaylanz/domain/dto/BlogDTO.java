package com.jaylanz.domain.dto;

import com.jaylanz.common.constant.PublishStatus;
import com.jaylanz.domain.po.BlogPO;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.sql.Timestamp;

@RedisHash("blog")
public class BlogDTO implements Serializable {
    @Id
    private Long id;
    private String title;
    private String description;
    private int readTime;
    private String content;
    private PublishStatus status;
    private int views;
    private Timestamp utcPublishTime;

    public BlogDTO() {
    }

    public BlogDTO(BlogPO po) {
        this(po.getId(), po.getTitle(), po.getDescription(), po.getReadTime(), po.getContent(), po.getStatus(),
                po.getViews(), po.getUtcPublishTime());
    }

    public BlogDTO(Long id, String title, String description, int readTime, String content, PublishStatus status, int views, Timestamp utcPublishTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.readTime = readTime;
        this.content = content;
        this.status = status;
        this.views = views;
        this.utcPublishTime = utcPublishTime;
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
}
