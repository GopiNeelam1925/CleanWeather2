package acodexm.cleanweather.data.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import acodexm.cleanweather.data.model.WeatherData;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface WeatherDao {

    @Query("SELECT * FROM " + WeatherData.TABLE_NAME + " WHERE " + WeatherData.LOCATION_FIELD + " = :location")
    LiveData<WeatherData> getWeatherData(String location);

    @Query("SELECT * FROM " + WeatherData.TABLE_NAME + " ORDER BY datetime(" + WeatherData.DATE_FIELD + ") DESC")
    LiveData<List<WeatherData>> getWeatherDataList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addWeatherData(WeatherData weatherData);

    @Delete
    void deleteWeatherData(WeatherData weatherData);

    @Update(onConflict = REPLACE)
    void updateWeatherData(WeatherData weatherData);

}
