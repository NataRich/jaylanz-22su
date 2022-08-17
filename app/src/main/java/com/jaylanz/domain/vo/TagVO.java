package com.jaylanz.domain.vo;

import com.jaylanz.common.validation.group.*;
import com.jaylanz.domain.dto.TagDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TagVO implements Serializable {
    @NotNull(groups = {OnProjectCreate.class, OnBlogCreate.class, OnTagUpdate.class})
    private Long id;
    @NotEmpty(groups = {OnTagCreate.class, OnTagUpdate.class})
    private String name;
    private int count;

    public TagVO() {
    }

    public TagVO(TagDTO dto) {
        this(dto.getId(), dto.getName(), dto.getCount());
    }

    public TagVO(Long id, String name, int count) {
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

    public static List<TagVO> transform(List<TagDTO> tags) {
        List<TagVO> ret = new ArrayList<>();
        for (TagDTO tag : tags)
            ret.add(new TagVO(tag));
        return ret;
    }
}
