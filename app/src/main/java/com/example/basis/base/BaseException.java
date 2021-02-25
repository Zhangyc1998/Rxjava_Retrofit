package com.example.basis.base;

import com.example.basis.http.HttpCode;

public class BaseException extends RuntimeException {
    private int errorCode = HttpCode.CODE_UNKNOWN;

    public BaseException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseException(){}

    public int getErrorCode() {
        return errorCode;
    }
}
