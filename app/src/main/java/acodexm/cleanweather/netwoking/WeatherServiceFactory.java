package acodexm.cleanweather.netwoking;

import android.arch.lifecycle.MutableLiveData;

import org.threeten.bp.LocalDateTime;

import acodexm.cleanweather.data.model.WeatherData;
import acodexm.cleanweather.data.model.forecast.WeatherDataForecast;
import acodexm.cleanweather.util.Constants;
import acodexm.cleanweather.view.viewmodel.Response;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class WeatherServiceFactory {
    private WeatherService mWeatherService;
    private MutableLiveData<Response> response = new MutableLiveData<>();

    public WeatherServiceFactory(WeatherService weatherService) {
        mWeatherService = weatherService;
    }

    public MutableLiveData<Response> response() {
        return response;
    }

    public void getWeather(String location, int days, String lang) {
        Observable<WeatherDataForecast> weatherData;
        weatherData = mWeatherService.getWeatherDataForecast(Constants.API_KEY, location, days, lang);
        weatherData
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherDataForecast>() {
                               @Override
                               public void onSubscribe(Disposable d) {
                                   Timber.d("on start subscription");
                                   response.setValue(Response.loading());
                               }

                               @Override
                               public void onNext(WeatherDataForecast data) {
                                   Timber.d("new data received %s", data.toString());
                                   response.setValue(Response.success(
                                           new WeatherData(data, LocalDateTime.now())));
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Timber.d("error");
                                   response.setValue(Response.error(new NetworkError(e)));
                               }

                               @Override
                               public void onComplete() {
                                   Timber.d("All data received");
                               }
                           }

//                        new Subscriber<WeatherDataForecast>() {
//                               @Override
//                               public void onCompleted() {
//                                   Timber.d("All data received");
//                               }
//
//                               @Override
//                               public void onError(Throwable e) {
//                                   Timber.d("error");
//                                   response.setValue(Response.error(new NetworkError(e)));
//                               }
//
//                               @Override
//                               public void onNext(WeatherDataForecast data) {
//                                   Timber.d("new data received %s",data.toString());
//                                   response.setValue(Response.success(
//                                           new WeatherData(data, LocalDateTime.now())));
//                               }
//
//                               @Override
//                               public void onStart() {
//                                   Timber.d("on start subscription");
//                                   super.onStart();
//                                   response.setValue(Response.loading());
//                               }
//                           }

//                        () ->response.setValue(Response.loading()),
//                                weatherDataForecast -> response.setValue(Response.success(
//                                        new WeatherData(weatherDataForecast, LocalDateTime.now())),
//                                        e -> response.setValue(Response.error(new NetworkError(e)))

//                        new Subscriber<WeatherDataForecast>() {
//                    @Override
//                    public void onCompleted() {
//                        Timber.d("All data received");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Timber.e("Error: %s", e.getMessage());
//                        callback.onError(new NetworkError(e));
//                    }
//
//                    @Override
//                    public void onNext(WeatherDataForecast weatherData) {
//                        Timber.d(weatherData.toString());
//                        callback.onSuccess(new WeatherData(weatherData, LocalDateTime.now()));
//
//                    }
//
//                }
                );
    }

}
