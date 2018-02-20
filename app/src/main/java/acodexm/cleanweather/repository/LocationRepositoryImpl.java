package acodexm.cleanweather.repository;


import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import acodexm.cleanweather.data.dao.LocationDao;
import acodexm.cleanweather.data.model.LocationData;
import io.reactivex.Completable;
import timber.log.Timber;

public class LocationRepositoryImpl implements LocationRepository {

    @Inject
    LocationDao locationDao;

    public LocationRepositoryImpl(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    @Override
    public Completable addLocation(final LocationData locationData) {
        if (locationData == null) {
            return Completable.error(new IllegalArgumentException("LocationData cannot be null"));
        }
        LocationData dbData = locationDao.getLocation(locationData.getLocation()).getValue();
        if (dbData != null && dbData.equals(locationData))
            return Completable.fromAction(() -> locationDao.updateLocation(new LocationData(locationData)));
        Timber.d("insert data to database %s", locationData.toString());
        return Completable.fromAction(() -> locationDao.addLocation(locationData));
    }

    @Override
    public LiveData<LocationData> getLocation(String location) {
        return locationDao.getLocation(location);
    }

    @Override
    public LiveData<LocationData> getCurrentLocation() {
        return locationDao.getCurrentLocation();
    }

    @Override
    public LiveData<List<LocationData>> getLocationList() {
        return locationDao.getLocationList();
    }

    @Override
    public Completable deleteLocation(final LocationData locationData) {
        if (locationData == null) {
            return Completable.error(new IllegalArgumentException("LocationData cannot be null"));
        }
        return Completable.fromAction(() -> locationDao.deleteLocation(locationData));
    }
}
