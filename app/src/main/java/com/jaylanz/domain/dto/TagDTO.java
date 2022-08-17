package com.jaylanz.domain.dto;

import com.jaylanz.domain.po.TagPO;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@RedisHash("tag")
public class TagDTO implements Serializable {
    @Id
    private Long id;
    @Indexed
    private String name;
    private int count;

    public TagDTO() {
    }

    public TagDTO(TagPO po) {
        this(po.getId(), po.getName(), po.getCount());
    }

    public TagDTO(Long id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
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
}
