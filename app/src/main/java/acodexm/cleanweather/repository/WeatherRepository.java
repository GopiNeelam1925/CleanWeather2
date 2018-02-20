package acodexm.cleanweather.repository;


import android.arch.lifecycle.LiveData;

import java.util.List;

import acodexm.cleanweather.data.model.WeatherData;
import io.reactivex.Completable;


public interface WeatherRepository {

    Completable addWeatherData(WeatherData weatherData);

    LiveData<WeatherData> getWeatherData(String location);

    LiveData<List<WeatherData>> getWeatherDataList();

    Completable deleteWeatherData(WeatherData weatherData);

}
