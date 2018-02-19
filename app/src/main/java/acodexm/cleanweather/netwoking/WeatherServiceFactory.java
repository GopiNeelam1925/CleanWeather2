package acodexm.cleanweather.netwoking;

import org.threeten.bp.LocalDateTime;

import acodexm.cleanweather.data.model.WeatherData;
import acodexm.cleanweather.data.model.forecast.WeatherDataForecast;
import acodexm.cleanweather.util.Constants;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import timber.log.Timber;


public class WeatherServiceFactory {
    private WeatherService mWeatherService;

    public WeatherServiceFactory(WeatherService weatherService) {
        mWeatherService = weatherService;
    }

    public Subscription getWeather(String location, int days, String lang, final GetDataCallback callback) {
        Observable<WeatherDataForecast> weatherData;
        weatherData = mWeatherService.getWeatherDataForecast(Constants.API_KEY, location, days, lang);
        return weatherData
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<WeatherDataForecast>() {
                    @Override
                    public void onCompleted() {
                        Timber.d("All data received");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("Error: %s", e.getMessage());
                        callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(WeatherDataForecast weatherData) {
                        Timber.d(weatherData.toString());
                        callback.onSuccess(new WeatherData(weatherData, LocalDateTime.now()));

                    }
                });
    }


    public interface GetDataCallback {

        void onSuccess(WeatherData weatherData);

        void onError(NetworkError networkError);
    }

}
