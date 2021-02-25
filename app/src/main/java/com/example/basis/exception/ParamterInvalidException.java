package com.example.basis.exception;

import com.example.basis.base.BaseException;
import com.example.basis.http.HttpCode;

public class ParamterInvalidException extends BaseException {

    public ParamterInvalidException() {
        super("参数有误", HttpCode.CODE_PARAMETER_INVALID);
    }
}
