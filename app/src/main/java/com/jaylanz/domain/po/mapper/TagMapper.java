package com.jaylanz.domain.po.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jaylanz.domain.po.TagPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TagMapper extends BaseMapper<TagPO> {
    @Select("SELECT * FROM t_tag WHERE name = #{tagName}")
    TagPO selectByName(String tagName);
}
