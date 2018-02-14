package acodexm.cleanweather.injection;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import acodexm.cleanweather.BaseApp;
import acodexm.cleanweather.data.dao.WeatherDao;
import acodexm.cleanweather.data.db.WeatherDatabase;
import acodexm.cleanweather.repository.WeatherRepository;
import acodexm.cleanweather.repository.WeatherRepositoryImpl;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;
import timber.log.Timber;


@Module(includes = {AndroidInjectionModule.class, ViewModelModule.class})
public class BaseModule {

    @Provides
    WeatherRepository providesWeatherRepository(WeatherDao weatherDao) {
        Timber.d("BaseModule providesEventRepository:%s", weatherDao);
        return new WeatherRepositoryImpl(weatherDao);
    }

    @SuppressLint("TimberArgCount")
    @Provides
    @Singleton
    WeatherDao providesWeatherDao(WeatherDatabase weatherDatabase) {
        Timber.d("BaseModule providesEventDao:%s", weatherDatabase);
        return weatherDatabase.weatherDao();
    }

    @Provides
    @Singleton
    WeatherDatabase providesWeatherDatabase(BaseApp context) {
        Timber.d("BaseModule providesEventDatabase");
        return Room.databaseBuilder(context.getApplicationContext(), WeatherDatabase.class, "weather_db").build();
    }
}
