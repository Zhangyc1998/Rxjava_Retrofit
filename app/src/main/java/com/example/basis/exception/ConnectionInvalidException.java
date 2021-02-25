package com.example.basis.exception;

import com.example.basis.base.BaseException;
import com.example.basis.http.HttpCode;

public class ConnectionInvalidException extends BaseException {

    public ConnectionInvalidException() {
        super("网络请求失败", HttpCode.CODE_CONNECTION_FAILED);
    }
}
