package com.example;

import com.example.basis.callback.RequestCallBack;
import com.example.model.Weather;

public interface IWeatherDataSource {
    public void queryWeather(String cityName, RequestCallBack<Weather> responseCallback);
}
