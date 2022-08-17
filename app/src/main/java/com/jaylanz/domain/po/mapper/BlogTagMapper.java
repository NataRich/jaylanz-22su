package com.jaylanz.domain.po.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jaylanz.domain.po.BlogTagPO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BlogTagMapper extends BaseMapper<BlogTagPO> {
    @Select("SELECT * FROM t_blog_tag WHERE blog_id = #{blogId}")
    List<BlogTagPO> selectAllByBlogId(Long blogId);

    @Delete("DELETE FROM t_blog_tag WHERE blog_id = #{blogId}")
    int deleteByBlogId(Long blogId);
}
