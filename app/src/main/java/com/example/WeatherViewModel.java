package com.example;

import io.reactivex.disposables.Disposable;
import com.example.basis.base.BaseViewModel;
import com.example.model.Weather;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class WeatherViewModel extends BaseViewModel {
    private MutableLiveData<Weather> weatherLiveData;

    private WeatherRepo weatherRepo;

    public WeatherViewModel() {
        weatherLiveData = new MutableLiveData<>();
        weatherRepo = new WeatherRepo(new WeatherDataSource(this));
    }

    public void queryWeather(String cityName) {
        weatherRepo.queryWeather(cityName).observe(lifecycleOwner, new Observer<Weather>() {
            @Override
            public void onChanged(@Nullable Weather weather) {
                weatherLiveData.setValue(weather);
            }
        });
    }

    public MutableLiveData<Weather> getWeatherLiveData() {
        return weatherLiveData;
    }
}
