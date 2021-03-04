package com.example.rxjava_retrofit;

import com.example.LViewModelProviders;
import com.example.WeatherViewModel;
import com.example.model.Weather;
import com.example.view.BaseActivity;
import com.google.gson.Gson;
import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModel;

public class MainActivity extends BaseActivity {
    private WeatherViewModel weatherViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //注意要清除 FLAG_TRANSLUCENT_STATUS flag
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        setContentView(R.layout.activity_main);
    }

    @Override
    protected ViewModel initViewModel() {
        weatherViewModel = LViewModelProviders.of(this, WeatherViewModel.class);
        weatherViewModel.getWeatherLiveData().observe(this, this::handlerWeather);
        return weatherViewModel;
    }
    private void handlerWeather(Weather weather) {
        StringBuilder result = new StringBuilder();
        for (Weather.InnerWeather.NearestWeather nearestWeather : weather.getData().getWeather()) {
            result.append("\n\n").append(new Gson().toJson(nearestWeather));
        }
//        tv_weather.setText(result.toString());
    }

    public void queryWeather(View view) {
//        tv_weather.setText(null);
//        weatherViewModel.queryWeather(et_cityName.getText().toString());
    }
}