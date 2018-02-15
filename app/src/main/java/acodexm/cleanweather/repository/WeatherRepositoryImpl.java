package acodexm.cleanweather.repository;

import android.arch.lifecycle.LiveData;

import javax.inject.Inject;

import acodexm.cleanweather.data.dao.WeatherDao;
import acodexm.cleanweather.data.model.WeatherData;
import acodexm.cleanweather.netwoking.WeatherServiceFactory;
import io.reactivex.Completable;


public class WeatherRepositoryImpl implements WeatherRepository {
    @Inject
    WeatherServiceFactory mService;
    @Inject
    WeatherDao weatherDao;
    private String location;

    public WeatherRepositoryImpl(WeatherDao weatherDao) {
        this.weatherDao = weatherDao;
    }

    @Override
    public Completable addWeatherData(final WeatherData weatherData) {
        if (weatherData == null) {
            return Completable.error(new IllegalArgumentException("WeatherData cannot be null"));
        }
        return Completable.fromAction(() -> weatherDao.addWeatherData(weatherData));
    }

    @Override
    public LiveData<WeatherData> getWeatherData() {
        return weatherDao.getWeatherData(location);
    }

    @Override
    public Completable deleteWeatherData(final WeatherData weatherData) {
        if (weatherData == null) {
            return Completable.error(new IllegalArgumentException("WeatherData cannot be null"));
        }
        return Completable.fromAction(() -> weatherDao.deleteWeatherData(weatherData));
    }
}
