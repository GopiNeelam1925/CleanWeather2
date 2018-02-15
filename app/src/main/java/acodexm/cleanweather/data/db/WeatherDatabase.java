package acodexm.cleanweather.data.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import acodexm.cleanweather.data.dao.WeatherDao;
import acodexm.cleanweather.data.model.WeatherData;

@Database(entities = {WeatherData.class}, version = 1)
@TypeConverters(ModelTypeConverter.class)
public abstract class WeatherDatabase extends RoomDatabase {

    public abstract WeatherDao weatherDao();

}
