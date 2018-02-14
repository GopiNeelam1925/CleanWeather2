package acodexm.cleanweather.repository;

import android.arch.lifecycle.LiveData;

import org.threeten.bp.LocalDateTime;

import javax.inject.Inject;

import acodexm.cleanweather.data.dao.WeatherDao;
import acodexm.cleanweather.data.model.WeatherData;
import io.reactivex.Completable;


public class WeatherRepositoryImpl implements WeatherRepository {

    @Inject
    WeatherDao weatherDao;

    public WeatherRepositoryImpl(WeatherDao weatherDao) {
        this.weatherDao = weatherDao;
    }

    @Override
    public Completable addWeatherData(final WeatherData weatherData) {
        if (weatherData == null) {
            return Completable.error(new IllegalArgumentException("WeatherDataCurrent cannot be null"));
        }
        return Completable.fromAction(() -> weatherDao.addWeatherData(weatherData));
    }

    @Override
    public LiveData<WeatherData> getWeatherData() {
        //Here is where we would do more complex logic, like getting events from a cache
        //then inserting into the database etc. In this example we just go straight to the dao.
        return weatherDao.getWeatherData(LocalDateTime.now());
    }

    @Override
    public Completable deleteWeatherData(final WeatherData weatherData) {
        if (weatherData == null) {
            return Completable.error(new IllegalArgumentException("WeatherDataCurrent cannot be null"));
        }
        return Completable.fromAction(() -> weatherDao.deleteWeatherData(weatherData));
    }
}
