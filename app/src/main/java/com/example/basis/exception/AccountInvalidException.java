package com.example.basis.exception;

import com.example.basis.base.BaseException;
import com.example.basis.http.HttpCode;

public class AccountInvalidException extends BaseException {

    public AccountInvalidException() {
        super("帐号密码错误", HttpCode.CODE_ACCOUNT_INVALID);
    }
}
