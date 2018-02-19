package acodexm.cleanweather.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.threeten.bp.LocalDateTime;

import acodexm.cleanweather.data.model.forecast.WeatherDataForecast;

import static acodexm.cleanweather.data.model.WeatherData.TABLE_NAME;


@Entity(tableName = TABLE_NAME)
public class WeatherData {

    public static final String TABLE_NAME = "weather_data";
    public static final String LOCATION_FIELD = "location";

    @PrimaryKey(autoGenerate = true)
    private int weatherID;

    @Embedded
    private WeatherDataForecast weatherDataForecast;
    private LocalDateTime date;
    @ColumnInfo(name = LOCATION_FIELD)
    private String locationName;

    public WeatherData() {
    }

    public WeatherData(WeatherDataForecast weatherDataForecast, LocalDateTime date) {
        this.weatherDataForecast = weatherDataForecast;
        this.date = date;
        this.locationName = weatherDataForecast.getLocation().getName();
    }

    public int getWeatherID() {
        return weatherID;
    }

    public void setWeatherID(int weatherID) {
        this.weatherID = weatherID;
    }


    public WeatherDataForecast getWeatherDataForecast() {
        return weatherDataForecast;
    }

    public void setWeatherDataForecast(WeatherDataForecast weatherDataForecast) {
        this.weatherDataForecast = weatherDataForecast;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "weatherID=" + weatherID +
                ", weatherDataForecast=" + weatherDataForecast +
                ", date=" + date +
                ", locationName='" + locationName + '\'' +
                '}';
    }
}
