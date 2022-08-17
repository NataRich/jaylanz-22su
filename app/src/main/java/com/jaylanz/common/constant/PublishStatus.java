package com.jaylanz.common.constant;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum PublishStatus implements IEnum<String> {
    PUBLISHED("PUBLISHED"),
    DRAFT("DRAFT");

    private final String status;

    PublishStatus(String status) {
        this.status = status;
    }

    @Override
    public String getValue() {
        return status;
    }
}
