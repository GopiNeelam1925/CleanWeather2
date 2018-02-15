
package acodexm.cleanweather.data.model.forecast;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static acodexm.cleanweather.data.model.forecast.WeatherDataForecast.WEATHER_FORECAST;

@Entity(tableName = WEATHER_FORECAST)
public class WeatherDataForecast {

    public static final String WEATHER_FORECAST = "weather_forecast";
    @PrimaryKey(autoGenerate = true)
    private int weatherForecastID;
    @SerializedName("location")
    @Expose
    @Embedded
    private Location location;
    @SerializedName("current")
    @Expose
    @Embedded
    private Current current;
    @SerializedName("forecast")
    @Expose
    @Embedded
    private Forecast forecast;

    public int getWeatherForecastID() {
        return weatherForecastID;
    }

    public void setWeatherForecastID(int weatherForecastID) {
        this.weatherForecastID = weatherForecastID;
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

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

}
