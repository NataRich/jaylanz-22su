package com.jaylanz.domain.po.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jaylanz.domain.po.ProjectPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectMapper extends BaseMapper<ProjectPO> {
}
