package com.example.basis;

import io.reactivex.observers.DisposableObserver;
import com.example.basis.base.BaseException;
import com.example.basis.base.BaseViewModel;
import com.example.basis.callback.RequestCallBack;
import com.example.basis.callback.RequestMultiplyCallback;
import com.example.basis.http.HttpCode;
import android.widget.Toast;

public class BaseSubscriber<T> extends DisposableObserver<T> {

    private BaseViewModel baseViewModel;
    private RequestCallBack<T> requestCallBack;

    public BaseSubscriber(BaseViewModel baseViewModel) {
        this.baseViewModel = baseViewModel;
    }

    public BaseSubscriber(BaseViewModel baseViewModel, RequestCallBack<T> requestCallBack) {
        this.baseViewModel = baseViewModel;
        this.requestCallBack = requestCallBack;
    }

    @Override
    public void onNext(T t) {
        if (requestCallBack!=null){
            requestCallBack.onSuccess(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (requestCallBack instanceof RequestMultiplyCallback) {
            RequestMultiplyCallback callback = (RequestMultiplyCallback) requestCallBack;
            if (e instanceof BaseException) {
                callback.onFail((BaseException) e);
            } else {
                callback.onFail(new BaseException(e.getMessage(),HttpCode.CODE_UNKNOWN));
            }
        } else {
//            if (baseViewModel == null) {
//                Toast.makeText(ContextHolder.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            } else {
//                baseViewModel.showToast(e.getMessage());
//            }
        }
    }

    @Override
    public void onComplete() {

    }
}
