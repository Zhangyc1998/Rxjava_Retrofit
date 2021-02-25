package com.example;

import com.example.basis.base.BaseViewModel;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

public class LViewModelProviders {

    public static <T extends BaseViewModel> T of(@NonNull FragmentActivity activity, Class<T> modelClass) {
        T t = ViewModelProviders.of(activity).get(modelClass);
        t.setLifecycleOwner(activity);
        return t;
    }

    public static <T extends BaseViewModel> T of(@NonNull Fragment activity, Class<T> modelClass) {
        T t = ViewModelProviders.of(activity).get(modelClass);
        t.setLifecycleOwner(activity);
        return t;
    }

}
