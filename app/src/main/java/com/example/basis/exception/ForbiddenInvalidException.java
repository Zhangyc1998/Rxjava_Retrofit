package com.example.basis.exception;

import com.example.basis.base.BaseException;
import com.example.basis.http.HttpCode;

public class ForbiddenInvalidException extends BaseException {

    public ForbiddenInvalidException() {
        super("禁止访问", HttpCode.CODE_FORBIDDEN);
    }
}
