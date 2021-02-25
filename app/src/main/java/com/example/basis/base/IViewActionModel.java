package com.example.basis.base;

import androidx.lifecycle.MutableLiveData;

public interface IViewActionModel {

    void startLoading();

    void startLoading(String message);

    void dismissLoading();

    void showToast(String message);

    void finish();

    void finishWithResultOk();

    MutableLiveData<BaseActionEvent> getActionLiveData();

}
