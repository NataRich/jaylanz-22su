package com.jaylanz.domain.dto;

import com.jaylanz.domain.po.ProjectTagPO;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@RedisHash("project_tag")
public class ProjectTagDTO implements Serializable {
    @Id
    private Long id;
    @Indexed
    private Long projId;
    private Long tagId;
    private int count;

    public ProjectTagDTO() {
    }

    public ProjectTagDTO(ProjectTagPO po) {
        this(po.getId(), po.getProjId(), po.getTagId(), po.getCount());
    }

    public ProjectTagDTO(Long id, Long projId, Long tagId, int count) {
        this.id = id;
        this.projId = projId;
        this.tagId = tagId;
        this.count = count;
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
}
