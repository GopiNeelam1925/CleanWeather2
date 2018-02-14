
package acodexm.cleanweather.data.model.current;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static acodexm.cleanweather.data.model.current.WeatherData.TABLE_NAME;


@Entity(tableName = TABLE_NAME)
public class WeatherData {
    public static final String TABLE_NAME = "weather_data";
    public static final String LOCATION = "location";
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("location")
    @Expose
    @ColumnInfo(name = LOCATION)
    private Location location;
    @SerializedName("current")
    @Expose
    private Current current;


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "location=" + location +
                ", current=" + current +
                '}';
    }
}
