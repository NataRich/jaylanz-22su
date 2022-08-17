package com.jaylanz.domain.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PagingRequest implements Serializable {
    @NotNull
    @Min(1)
    private Long pageNum;

    @NotNull
    @Min(1)
    private Long pageSize;

    public PagingRequest() {
    }

    public PagingRequest(Long pageNum, Long pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }
}
