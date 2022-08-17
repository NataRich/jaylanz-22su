package com.jaylanz.domain.po.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jaylanz.domain.po.ProjectTagPO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProjectTagMapper extends BaseMapper<ProjectTagPO> {
    @Select("SELECT * FROM t_proj_tag WHERE proj_id = #{projId}")
    List<ProjectTagPO> selectAllByProjId(Long projId);

    @Delete("DELETE FROM t_proj_tag WHERE proj_id = #{projId}")
    int deleteByProjId(Long projId);
}
