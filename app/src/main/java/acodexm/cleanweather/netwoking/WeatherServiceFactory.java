package acodexm.cleanweather.netwoking;


import org.threeten.bp.LocalDateTime;

import acodexm.cleanweather.data.model.WeatherData;
import acodexm.cleanweather.util.Constants;
import io.reactivex.android.schedulers.AndroidSchedulers;
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

    public Subscription getWeather(String location, String geoLocation, int days, String lang,
                                   final GetDataCallback callback) {
        Observable<WeatherData> weatherData;
        if (!geoLocation.isEmpty()) {
            weatherData = Observable.zip(
                    mWeatherService.getWeatherData(Constants.API_KEY, geoLocation, lang),
                    mWeatherService.getWeatherDataForecast(Constants.API_KEY, geoLocation, days, lang),
                    (weatherDataCurrent, weatherDataForecast) -> new WeatherData(weatherDataCurrent,
                            weatherDataForecast, LocalDateTime.now(), weatherDataCurrent.getLocation().getName()));
        } else {
            weatherData = Observable.zip(
                    mWeatherService.getWeatherData(Constants.API_KEY, location, lang),
                    mWeatherService.getWeatherDataForecast(Constants.API_KEY, location, days, lang),
                    (weatherDataCurrent, weatherDataForecast) -> new WeatherData(weatherDataCurrent,
                            weatherDataForecast, LocalDateTime.now(), weatherDataCurrent.getLocation().getName()));
        }
        return weatherData
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherData>() {
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
                    public void onNext(WeatherData weatherData) {
                        Timber.d(weatherData.toString());
                        callback.onSuccess(weatherData);

                    }
                });
    }


    public interface GetDataCallback {

        void onSuccess(WeatherData weatherData);

        void onError(NetworkError networkError);
    }

}
