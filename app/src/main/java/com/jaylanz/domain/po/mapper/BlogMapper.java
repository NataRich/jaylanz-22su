package com.jaylanz.domain.po.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jaylanz.domain.po.BlogPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BlogMapper extends BaseMapper<BlogPO> {
    @Select("SELECT * FROM t_blog WHERE title = '%#{title}%'")
    List<BlogPO> selectByTitleLike(String title);
}
