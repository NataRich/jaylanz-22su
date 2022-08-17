package com.jaylanz.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;

import java.io.Serializable;

@TableName("t_blog_tag")
public class BlogTagPO implements Serializable {
    @TableId
    private Long id;
    private Long blogId;
    private Long tagId;
    private int count;
    @Version
    private int version;

    public BlogTagPO() {
    }

    public BlogTagPO(Long id, Long blogId, Long tagId, int count, int version) {
        this.id = id;
        this.blogId = blogId;
        this.tagId = tagId;
        this.count = count;
        this.version = version;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
