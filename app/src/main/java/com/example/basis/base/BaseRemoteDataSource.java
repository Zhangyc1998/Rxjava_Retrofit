package com.example.basis.base;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import com.example.basis.BaseSubscriber;
import com.example.basis.RetrofitManagement;
import com.example.basis.callback.RequestCallBack;
import com.example.basis.callback.RequestMultiplyCallback;
import com.example.basis.model.BaseResponseBody;

public abstract class BaseRemoteDataSource {

    private CompositeDisposable compositeDisposable;

    private BaseViewModel baseViewModel;

    public BaseRemoteDataSource(BaseViewModel baseViewModel) {
        this.compositeDisposable = new CompositeDisposable();
        this.baseViewModel = baseViewModel;
    }

    protected <T> T getService(Class<T> clz) {
        return RetrofitManagement.getInstance().getService(clz);
    }

    protected <T> T getService(Class<T> clz, String host) {
        return RetrofitManagement.getInstance().getService(clz, host);
    }

    private <T> ObservableTransformer<BaseResponseBody<T>, T> applySchedulers() {
        return RetrofitManagement.getInstance().applySchedulers();
    }

    protected <T> void execute(Observable observable, RequestCallBack<T> callback) {
        execute(observable, new BaseSubscriber<>(baseViewModel, callback), true);
    }

    protected <T> void execute(Observable observable, RequestMultiplyCallback<T> callback) {
        execute(observable, new BaseSubscriber<>(baseViewModel, callback), true);
    }

    public void executeWithoutDismiss(Observable observable, Observer observer) {
        execute(observable, observer, false);
    }

    private void execute(Observable observable, Observer observer, boolean isDismiss) {
        Disposable disposable = (Disposable) observable
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(applySchedulers())
                .compose(isDismiss ? loadingTransformer() : loadingTransformerWithoutDismiss())
                .subscribeWith(observer);
        addDisposable(disposable);
    }

    private void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void dispose() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    private void startLoading() {
        if (baseViewModel != null) {
            baseViewModel.startLoading();
        }
    }

    private void dismissLoading() {
        if (baseViewModel != null) {
            baseViewModel.dismissLoading();
        }
    }

    private <T> ObservableTransformer<T, T> loadingTransformer() {
        return observable -> observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> startLoading())
                .doFinally(this::dismissLoading);
    }

    private <T> ObservableTransformer<T, T> loadingTransformerWithoutDismiss() {
        return observable -> observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> startLoading());
    }


}
