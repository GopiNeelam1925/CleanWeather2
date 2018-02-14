package acodexm.cleanweather.repository;


import android.arch.lifecycle.LiveData;

import acodexm.cleanweather.data.model.WeatherData;
import io.reactivex.Completable;


public interface WeatherRepository {

    Completable addWeatherData(WeatherData weatherData);

    LiveData<WeatherData> getWeatherData();

    Completable deleteWeatherData(WeatherData weatherData);

}
