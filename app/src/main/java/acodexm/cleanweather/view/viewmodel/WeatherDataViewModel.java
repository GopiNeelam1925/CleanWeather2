package acodexm.cleanweather.view.viewmodel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import acodexm.cleanweather.data.model.WeatherData;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class WeatherDataViewModel extends BaseViewModel {

    @Inject
    public WeatherDataViewModel() {
    }

    public MutableLiveData<Response> fetchWeather(String location, int days, String lang) {
        mService.getWeather(location, days, lang);
        return mService.response();
    }


    public LiveData<List<WeatherData>> getWeatherDataList() {
        return weatherRepository.getWeatherDataList();
    }

    public void addWeatherData(WeatherData weatherData) {
        weatherRepository.addWeatherData(weatherData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete - successfully added weather to darabase");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e, "onError - add:");
                    }
                });
    }


    public LiveData<WeatherData> getWeatherData(String location) {
        Timber.d("getWeatherData for %s", location);
        return weatherRepository.getWeatherData(location.toLowerCase().trim());
    }

    public void deleteWeatherData(WeatherData weatherData) {
        weatherRepository.deleteWeatherData(weatherData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete - deleted weatherDataCurrent");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "OnError - deleted weatherDataCurrent: ");
                    }
                });
    }

}
