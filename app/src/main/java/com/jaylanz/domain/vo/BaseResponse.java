package com.jaylanz.domain.vo;

import com.jaylanz.common.response.ResponseCode;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

public class BaseResponse<T> implements Serializable {
    private int code;
    private String message;
    private T body;
    private Date timestamp;

    public BaseResponse(int code, String message, T body) {
        this.code = code;
        this.message = message;
        this.body = body;
        this.timestamp = Date.from(Instant.now());
    }

    public BaseResponse(ResponseCode code, T body) {
        this(code.value(), code.message(), body);
    }

    public BaseResponse(ResponseCode code) {
        this(code,null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public static <V> BaseResponse<V> OK() {
        return new BaseResponse<V>(ResponseCode.SUCCESS, null);
    }

    public static <V> BaseResponse<V> OK(V body) {
        return new BaseResponse<V>(ResponseCode.SUCCESS, body);
    }

    public static <V> BaseResponse<V> NOT_FOUND() {
        return new BaseResponse<>(ResponseCode.NOT_FOUND, null);
    }

    public static <V> BaseResponse<V> DUPLICATE() {
        return new BaseResponse<>(ResponseCode.DUPLICATE, null);
    }

    public static <V> BaseResponse<V> BAD_PARAMS() {
        return new BaseResponse<>(ResponseCode.BAD_PARAMS, null);
    }

    public static <V> BaseResponse<V> ERROR() {
        return new BaseResponse<>(ResponseCode.ERROR, null);
    }
}
