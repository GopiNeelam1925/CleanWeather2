package acodexm.cleanweather.repository;


import android.arch.lifecycle.LiveData;

import java.util.List;

import acodexm.cleanweather.data.model.current.WeatherData;
import io.reactivex.Completable;


public interface WeatherRepository {

    Completable addWeatherData(WeatherData weatherData);

    LiveData<List<WeatherData>> getWeatherData();

    Completable deleteWeatherData(WeatherData weatherData);


}
