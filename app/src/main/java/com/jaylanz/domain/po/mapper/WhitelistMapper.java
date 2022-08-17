package com.jaylanz.domain.po.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jaylanz.domain.po.UserPO;
import com.jaylanz.domain.po.WhitelistPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WhitelistMapper extends BaseMapper<WhitelistPO> {
    @Select("SELECT * FROM t_whitelist WHERE ip_address = #{ipAddress}")
    UserPO selectByIpAddress(String ipAddress);
}
