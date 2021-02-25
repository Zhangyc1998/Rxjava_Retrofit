package com.example;

import com.example.basis.base.BaseRepo;
import com.example.basis.callback.RequestCallBack;
import com.example.model.Weather;
import androidx.lifecycle.MutableLiveData;

public class WeatherRepo extends BaseRepo<IWeatherDataSource> {
    public WeatherRepo(IWeatherDataSource remoteDataSource) {
        super(remoteDataSource);
    }

    public MutableLiveData<Weather> queryWeather(String cityName) {
        MutableLiveData<Weather> weatherMutableLiveData = new MutableLiveData<>();
        remoteDataSource.queryWeather(cityName, new RequestCallBack<Weather>() {
            @Override
            public void onSuccess(Weather weather) {
                weatherMutableLiveData.setValue(weather);
            }
        });
        return weatherMutableLiveData;
    }

}
