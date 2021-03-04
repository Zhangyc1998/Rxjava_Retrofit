package com.example.basis;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;
import com.example.basis.exception.AccountInvalidException;
import com.example.basis.exception.ResultInvalidException;
import com.example.basis.exception.TokenInvalidException;
import com.example.basis.http.HttpCode;
import com.example.basis.http.HttpConfig;
import com.example.basis.interceptor.FilterInterceptor;
import com.example.basis.interceptor.HeaderInterceptor;
import com.example.basis.interceptor.HttpInterceptor;
import com.example.basis.model.BaseResponseBody;
import com.example.rxjava_retrofit.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManagement {

    private static final long READ_TIMEOUT = 6000;
    private static final long WRITE_TIMEOUT = 6000;
    private static final long CONNECT_TIMEOUT = 6000;
    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    public RetrofitManagement() {
    }

    public static RetrofitManagement getInstance() {
        return RetrofitHolder.retrofitManagement;
    }

    public static class RetrofitHolder {

        private static final RetrofitManagement retrofitManagement = new RetrofitManagement();
    }

    private Retrofit createRetrofit(String url) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.MICROSECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MICROSECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MICROSECONDS)
                .addInterceptor(new HttpInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new FilterInterceptor())
                .retryOnConnectionFailure(true);
        if (BuildConfig.DEBUG) {
            //添加打印请求日志，和返回参数
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        OkHttpClient client = builder.build();
        return new Retrofit.Builder().client(client)
                                     .baseUrl(url)
                                     .addConverterFactory(GsonConverterFactory.create())
                                     .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                     .build();
    }

    public <T> ObservableTransformer<BaseResponseBody<T>, T> applySchedulers() {
        return new ObservableTransformer<BaseResponseBody<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponseBody<T>> observable) {
                return observable.subscribeOn(Schedulers.io())
                                 .unsubscribeOn(Schedulers.io())
                                 .observeOn(AndroidSchedulers.mainThread())
                                 .flatMap(result -> {
                                     switch (result.getCode()) {
                                         case HttpCode.CODE_SUCCESS: {
                                             return RetrofitManagement.this.createData(result.getData());
                                         }
                                         case HttpCode.CODE_TOKEN_INVALID: {
                                             throw new TokenInvalidException();
                                         }
                                         case HttpCode.CODE_ACCOUNT_INVALID: {
                                             throw new AccountInvalidException();
                                         }
                                         default: {
                                             throw new ResultInvalidException();
                                         }
                                     }
                                 });
            }
        };
    }

    private <T> Observable<T> createData(T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(data);
                    emitter.onComplete();
                }
                catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    public <T> T getService(Class<T> clz) {
        return getService(clz, HttpConfig.BASE_URL_WEATHER);
    }

    //用map保存单一apiService
    public <T> T getService(Class<T> clz, String host) {
        T value;
        if (serviceMap.containsKey(host)) {
            Object obj = serviceMap.get(host);
            if (obj == null) {
                value = createRetrofit(host).create(clz);
                serviceMap.put(host, value);
            }
            else {
                value = (T)obj;
            }
        }
        else {
            value = createRetrofit(host).create(clz);
            serviceMap.put(host, value);
        }
        return value;
    }

}
