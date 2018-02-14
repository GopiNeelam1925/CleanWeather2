package acodexm.cleanweather.view.list;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import acodexm.cleanweather.data.model.current.WeatherData;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class WeatherDataViewModel extends ViewModel {

    @Inject
    WeatherDataRepository weatherDataRepository;

    @Inject
    public WeatherDataListViewModel() {
    }

    LiveData<List<WeatherData>> getWeatherDatas() {
        return weatherDataRepository.getWeatherDatas();
    }

    void deleteWeatherData(WeatherData weatherData) {
        weatherDataRepository.deleteWeatherData(weatherData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete - deleted weatherData");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "OnError - deleted weatherData: ");
                    }
                });
    }

}
