package com.example.basis.exception;

import com.example.basis.base.BaseException;
import com.example.basis.http.HttpCode;

public class ResultInvalidException extends BaseException {

    public ResultInvalidException() {
        super("返回类型错误", HttpCode.CODE_RESULT_INVALID);
    }
}
