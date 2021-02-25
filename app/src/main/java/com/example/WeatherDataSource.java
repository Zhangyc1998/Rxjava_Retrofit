package com.example;

import com.example.basis.base.BaseRemoteDataSource;
import com.example.basis.base.BaseViewModel;
import com.example.basis.callback.RequestCallBack;
import com.example.basis.service.ApiService;
import com.example.model.Weather;

public class WeatherDataSource extends BaseRemoteDataSource implements IWeatherDataSource {
    public WeatherDataSource(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void queryWeather(String cityName, RequestCallBack<Weather> responseCallback) {
        execute(getService(ApiService.class).queryWeather(cityName), responseCallback);
    }
}
