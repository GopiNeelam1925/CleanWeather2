package acodexm.cleanweather.repository;


import android.arch.lifecycle.LiveData;

import java.util.List;

import acodexm.cleanweather.data.model.LocationData;
import io.reactivex.Completable;

public interface LocationRepository {
    Completable addLocation(LocationData weatherData);

    LiveData<LocationData> getLocation(String location);

    LiveData<LocationData> getCurrentLocation();

    LiveData<List<LocationData>> getLocationList();

    Completable deleteLocation(LocationData weatherData);
}
