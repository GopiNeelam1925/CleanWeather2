package acodexm.cleanweather.view;


import android.util.Log;

import java.util.List;

import acodexm.cleanweather.model.openweathermap.WeatherData;
import acodexm.cleanweather.netwoking.NetworkError;
import acodexm.cleanweather.netwoking.WeatherServiceFactory;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class HomePresenter {
    private static String TAG = HomePresenter.class.getSimpleName();
    private final WeatherServiceFactory mService;
    private final HomeView mView;
    private CompositeSubscription mSubscription;

    public HomePresenter(WeatherServiceFactory service, HomeView view) {
        this.mService = service;
        this.mView = view;
        this.mSubscription = new CompositeSubscription();
    }

    public void getWeather(String location, List<Double> geoLocation, String days, String units) {
        mView.showWait();
        try {
            Subscription subscription = mService.getWeather(location, geoLocation, days, units,
                    new WeatherServiceFactory.GetDataCallback() {

                        @Override
                        public void onSuccess(WeatherData weatherData) {
                            Log.d(TAG, "onSuccess: " + weatherData.toString());
                            mView.removeWait();
                            mView.setWeatherView(weatherData);
                        }

                        @Override
                        public void onError(NetworkError networkError) {
                            mView.removeWait();
                            mView.onFailure(networkError.getAppErrorMessage());
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
            Log.e("onDestroy", e.getMessage());
        }
    }
}
