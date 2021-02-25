package com.example.basis.service;

import io.reactivex.Observable;
import com.example.basis.http.HttpConfig;
import com.example.basis.model.BaseResponseBody;
import com.example.model.Weather;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiService {
    @Headers({ HttpConfig.HTTP_REQUEST_TYPE_KEY + ":" + HttpConfig.HTTP_REQUEST_WEATHER})
    @GET("onebox/weather/query")
    Observable<BaseResponseBody<Weather>> queryWeather(@Query("cityname") String cityName);
}
