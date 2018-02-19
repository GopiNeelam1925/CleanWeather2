package acodexm.cleanweather.view.viewmodel;


import android.arch.lifecycle.LiveData;

import javax.inject.Inject;

import acodexm.cleanweather.data.model.WeatherData;
import acodexm.cleanweather.netwoking.NetworkError;
import acodexm.cleanweather.netwoking.WeatherServiceFactory;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class WeatherDataViewModel extends BaseViewModel {

    @Inject
    WeatherServiceFactory mService;
    private CompositeSubscription mSubscription;

    @Inject
    public WeatherDataViewModel() {
        mSubscription = new CompositeSubscription();
    }

    public void getWeather(String location, int days, String lang) {
        try {
            Subscription subscription = mService.getWeather(location, days, lang,
                    new WeatherServiceFactory.GetDataCallback() {
                        @Override
                        public void onSuccess(WeatherData weatherData) {
                            Timber.d("onSuccess: %s", weatherData.toString());
                            addWeatherData(weatherData);
                        }

                        @Override
                        public void onError(NetworkError networkError) {
                            Timber.d("onError: %s", networkError.getMessage());
                        }
                    });
            mSubscription.add(subscription);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onStop() {
        try {
            mSubscription.unsubscribe();
        } catch (Exception e) {
            Timber.e("onDestroy  %s", e.getMessage());
        }
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
        LiveData<WeatherData> data = weatherRepository.getWeatherData(location);
        WeatherData weatherData = data.getValue();
        if (weatherData == null) weatherData = new WeatherData();
        Timber.d("data from db %s", weatherData.toString());
        return data;
    }

    //TODO delete from sidebar adapter
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
