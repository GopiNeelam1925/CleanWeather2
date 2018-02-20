package acodexm.cleanweather.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import acodexm.cleanweather.data.dao.LocationDao;
import acodexm.cleanweather.data.model.LocationData;

@Database(entities = {LocationData.class}, version = 1)
@TypeConverters(ModelTypeConverter.class)
public abstract class LocationDatabase extends RoomDatabase {
    public abstract LocationDao locationDao();
}
