
package acodexm.cleanweather.data.model.current;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static acodexm.cleanweather.data.model.current.WeatherDataCurrent.WEATHER_CURRENT;

@Entity(tableName = WEATHER_CURRENT)
public class WeatherDataCurrent {
    public static final String WEATHER_CURRENT = "weather_current";
    @PrimaryKey(autoGenerate = true)
    private int weatherCurrentID;
    @SerializedName("locationCurrent")
    @Expose
    @Embedded
    private LocationCurrent locationCurrent;
    @SerializedName("currentCurrent")
    @Expose
    @Embedded
    private CurrentCurrent currentCurrent;

    public int getWeatherCurrentID() {
        return weatherCurrentID;
    }

    public void setWeatherCurrentID(int weatherCurrentID) {
        this.weatherCurrentID = weatherCurrentID;
    }

    public LocationCurrent getLocationCurrent() {
        return locationCurrent;
    }

    public void setLocationCurrent(LocationCurrent locationCurrent) {
        this.locationCurrent = locationCurrent;
    }

    public CurrentCurrent getCurrentCurrent() {
        return currentCurrent;
    }

    public void setCurrentCurrent(CurrentCurrent currentCurrent) {
        this.currentCurrent = currentCurrent;
    }

    @Override
    public String toString() {
        return "WeatherDataCurrent{" +
                "locationCurrent=" + locationCurrent +
                ", currentCurrent=" + currentCurrent +
                '}';
    }
}
