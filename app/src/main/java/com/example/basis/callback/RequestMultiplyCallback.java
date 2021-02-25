package com.example.basis.callback;

import com.example.basis.base.BaseException;

public interface RequestMultiplyCallback<T> extends RequestCallBack<T> {
    void onFail(BaseException exception);


}
