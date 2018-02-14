package acodexm.cleanweather.netwoking;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public WeatherService providesNetworkService() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://api.apixu.com/v1/")
                .build();

        return retrofit.create(WeatherService.class);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public WeatherServiceFactory providesService(WeatherService weatherService) {
        return new WeatherServiceFactory(weatherService);
    }

}
