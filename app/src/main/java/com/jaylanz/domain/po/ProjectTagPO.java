package com.jaylanz.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;

import java.io.Serializable;

@TableName("t_proj_tag")
public class ProjectTagPO implements Serializable {
    @TableId
    private Long id;
    private Long projId;
    private Long tagId;
    private int count;
    @Version
    private int version;

    public ProjectTagPO() {
    }

    public ProjectTagPO(Long id, Long projId, Long tagId, int count, int version) {
        this.id = id;
        this.projId = projId;
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

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
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
