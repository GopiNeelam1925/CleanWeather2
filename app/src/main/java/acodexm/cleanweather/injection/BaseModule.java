package acodexm.cleanweather.injection;

import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import acodexm.cleanweather.BaseApp;
import acodexm.cleanweather.data.dao.WeatherDao;
import acodexm.cleanweather.data.db.WeatherDatabase;
import acodexm.cleanweather.netwoking.WeatherService;
import acodexm.cleanweather.netwoking.WeatherServiceFactory;
import acodexm.cleanweather.repository.WeatherRepository;
import acodexm.cleanweather.repository.WeatherRepositoryImpl;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;


@Module(includes = {AndroidInjectionModule.class, ViewModelModule.class})
public class BaseModule {
    public static final String DB_NAME = "weather_db";

    @Provides
    WeatherRepository providesWeatherRepository(WeatherDao weatherDao) {
        Timber.d("BaseModule providesWeatherDataRepository:%s", weatherDao);
        return new WeatherRepositoryImpl(weatherDao);
    }

    @Provides
    @Singleton
    WeatherDao providesWeatherDao(WeatherDatabase weatherDatabase) {
        Timber.d("BaseModule providesWeatherDataDao:%s", weatherDatabase);
        return weatherDatabase.weatherDao();
    }

    @Provides
    @Singleton
    WeatherDatabase providesWeatherDatabase(BaseApp context) {
        Timber.d("BaseModule providesWeatherDataDatabase");
        return Room.databaseBuilder(context.getApplicationContext(), WeatherDatabase.class, DB_NAME).build();
    }

    @Provides
    @Singleton
    WeatherService providesNetworkService() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://api.apixu.com/v1/")
                .build();

        return retrofit.create(WeatherService.class);
    }

    @Provides
    @Singleton
    WeatherServiceFactory providesService(WeatherService weatherService) {
        return new WeatherServiceFactory(weatherService);
    }
}
