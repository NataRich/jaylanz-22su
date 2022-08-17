package com.jaylanz.common.response;

public enum ResponseCode {
    SUCCESS(2000, "OK"),

    NOT_FOUND(4000, "resource not found"),
    DUPLICATE(4001, "resource duplication"),
    BAD_PARAMS(4002, "bad parameters"),

    ERROR(5000, "unknown server error"),
    DB_OP_ERROR(5001, "database operation error")
    ;

    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int value() {
        return code;
    }

    public String message() {
        return message;
    }
}
