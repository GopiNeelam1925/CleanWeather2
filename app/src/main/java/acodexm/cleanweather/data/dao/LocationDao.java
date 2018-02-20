package acodexm.cleanweather.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import acodexm.cleanweather.data.model.LocationData;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface LocationDao {
    @Query("SELECT * FROM " + LocationData.TABLE_NAME + " WHERE " + LocationData.LOCATION_FIELD + " = :location")
    LiveData<LocationData> getLocation(String location);

    @Query("SELECT * FROM " + LocationData.TABLE_NAME + " ORDER BY " + LocationData.DATE_FIELD + " DESC LIMIT 1")
    LiveData<LocationData> getCurrentLocation();

    @Query("SELECT * FROM " + LocationData.TABLE_NAME + " ORDER BY " + LocationData.DATE_FIELD + " DESC")
    LiveData<List<LocationData>> getLocationList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addLocation(LocationData weatherData);

    @Delete
    void deleteLocation(LocationData weatherData);

    @Update(onConflict = REPLACE)
    void updateLocation(LocationData weatherData);
}
