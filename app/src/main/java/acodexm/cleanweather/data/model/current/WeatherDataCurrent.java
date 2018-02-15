
package acodexm.cleanweather.data.model.current;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static acodexm.cleanweather.data.model.current.WeatherDataCurrent.WEATHER_CURRENT;

@Entity(tableName = WEATHER_CURRENT)
public class WeatherDataCurrent {
    public static final String WEATHER_CURRENT = "weather_current";
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("current")
    @Expose
    private Current current;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
        return "WeatherDataCurrent{" +
                "location=" + location +
                ", current=" + current +
                '}';
    }
}
