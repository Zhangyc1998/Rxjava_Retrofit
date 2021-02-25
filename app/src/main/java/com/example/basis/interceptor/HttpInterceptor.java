package com.example.basis.interceptor;

import java.io.IOException;

import com.example.basis.exception.ConnectionInvalidException;
import com.example.basis.exception.ResultInvalidException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;

public class HttpInterceptor implements Interceptor {

    public HttpInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response originalResponse;
        try {
            originalResponse = chain.proceed(request);
        } catch (Exception e) {
            throw new ConnectionInvalidException();
        }
        if (originalResponse.code() != 200) {
            throw new ResultInvalidException();
        }
        BufferedSource source = originalResponse.body().source();
        source.request(Integer.MAX_VALUE);
        String byteString = source.buffer().snapshot().utf8();
        ResponseBody responseBody = ResponseBody.create(null, byteString);
        return originalResponse.newBuilder().body(responseBody).build();
    }
}
