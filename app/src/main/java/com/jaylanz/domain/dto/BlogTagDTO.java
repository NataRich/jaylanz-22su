package com.jaylanz.domain.dto;

import com.jaylanz.domain.po.BlogTagPO;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@RedisHash("blog_tag")
public class BlogTagDTO implements Serializable {
    @Id
    private Long id;
    @Indexed
    private Long blogId;
    private Long tagId;
    private int count;

    public BlogTagDTO() {
    }

    public BlogTagDTO(BlogTagPO po) {
        this(po.getId(), po.getBlogId(), po.getTagId(), po.getCount());
    }

    public BlogTagDTO(Long id, Long blogId, Long tagId, int count) {
        this.id = id;
        this.blogId = blogId;
        this.tagId = tagId;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
