package acodexm.cleanweather.view.viewmodel;


import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import acodexm.cleanweather.data.model.LocationData;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LocationDataViewModel extends BaseViewModel {

    @Inject
    public LocationDataViewModel() {
    }

    public void addLocation(LocationData locationData) {
        locationRepository.addLocation(locationData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete - successfully added locationData to database");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e, "onError - add:");
                    }
                });
    }

    public LiveData<List<LocationData>> getLocationList() {
        LiveData<List<LocationData>> listLiveData = locationRepository.getLocationList();
        Timber.d(" location list from location_db %s", listLiveData.getValue());
        return listLiveData;
    }

    public LiveData<LocationData> getCurrentLocation() {
        LiveData<LocationData> data = locationRepository.getCurrentLocation();
        Timber.d("current location from location_db %s", data.getValue());
        return data;
    }

    public LiveData<LocationData> getLocation(String query) {
        LiveData<LocationData> data = locationRepository.getLocation(query.toLowerCase().trim());
        LocationData locationData = data.getValue();
        if (locationData == null) locationData = new LocationData();
        Timber.d("data from location_db %s", locationData.toString());
        return data;
    }

    public void deleteLocation(LocationData locationData) {
        locationRepository.deleteLocation(locationData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete - deleted locationCurrent");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "OnError - deleted locationCurrent: ");
                    }
                });
    }
}
