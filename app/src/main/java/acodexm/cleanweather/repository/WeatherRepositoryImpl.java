package acodexm.cleanweather.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import acodexm.cleanweather.data.dao.WeatherDao;
import acodexm.cleanweather.data.model.WeatherData;
import acodexm.cleanweather.netwoking.WeatherServiceFactory;
import io.reactivex.Completable;
import timber.log.Timber;


public class WeatherRepositoryImpl implements WeatherRepository {
    @Inject
    WeatherServiceFactory mService;
    @Inject
    WeatherDao weatherDao;

    public WeatherRepositoryImpl(WeatherDao weatherDao) {
        this.weatherDao = weatherDao;
    }

    @Override
    public Completable addWeatherData(final WeatherData weatherData) {
        if (weatherData == null) {
            return Completable.error(new IllegalArgumentException("WeatherData cannot be null"));
        }
        WeatherData dbData = weatherDao.getWeatherData(weatherData.getLocationName()).getValue();
        if (dbData != null && dbData.equals(weatherData))
            return Completable.fromAction(() -> weatherDao.updateWeatherData(weatherData));
        Timber.d("insert data to database %s", weatherData.toString());
        return Completable.fromAction(() -> weatherDao.addWeatherData(weatherData));
    }

    @Override
    public LiveData<WeatherData> getWeatherData(String location) {
        return weatherDao.getWeatherData(location);
    }

    @Override
    public LiveData<List<WeatherData>> getWeatherDataList() {
        return weatherDao.getWeatherDataList();
    }

    @Override
    public Completable deleteWeatherData(final WeatherData weatherData) {
        if (weatherData == null) {
            return Completable.error(new IllegalArgumentException("WeatherData cannot be null"));
        }
        return Completable.fromAction(() -> weatherDao.deleteWeatherData(weatherData));
    }
}
