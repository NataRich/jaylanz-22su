package com.jaylanz.framework.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
public class AutoUpdateTimeHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("utcCreateTime", Timestamp.from(Instant.now()), metaObject);
        this.setFieldValByName("utcUpdateTime", Timestamp.from(Instant.now()), metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("utcUpdateTime", Timestamp.from(Instant.now()), metaObject);
    }
}
