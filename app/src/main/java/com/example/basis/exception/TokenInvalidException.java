package com.example.basis.exception;

import com.example.basis.base.BaseException;
import com.example.basis.http.HttpCode;

public class TokenInvalidException extends BaseException {

    public TokenInvalidException() {
        super("token失效", HttpCode.CODE_TOKEN_INVALID);
    }
}
