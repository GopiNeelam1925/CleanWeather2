package acodexm.cleanweather.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.threeten.bp.LocalDateTime;

import acodexm.cleanweather.data.model.current.WeatherDataCurrent;
import acodexm.cleanweather.data.model.forecast.WeatherDataForecast;

import static acodexm.cleanweather.data.model.WeatherData.TABLE_NAME;


@Entity(tableName = TABLE_NAME)
public class WeatherData {

    public static final String TABLE_NAME = "weather_data";
    public static final String LOCATION_FIELD = "location";

    @PrimaryKey(autoGenerate = true)
    private int id;
    private WeatherDataCurrent weatherDataCurrent;
    private WeatherDataForecast weatherDataForecast;
    private LocalDateTime date;
    @ColumnInfo(name = LOCATION_FIELD)
    private String location;


    public WeatherData(WeatherDataCurrent weatherDataCurrent, WeatherDataForecast weatherDataForecast, LocalDateTime date, String location) {
        this.weatherDataCurrent = weatherDataCurrent;
        this.weatherDataForecast = weatherDataForecast;
        this.date = date;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WeatherDataCurrent getWeatherDataCurrent() {
        return weatherDataCurrent;
    }

    public void setWeatherDataCurrent(WeatherDataCurrent weatherDataCurrent) {
        this.weatherDataCurrent = weatherDataCurrent;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "id=" + id +
                ", weatherDataCurrent=" + weatherDataCurrent +
                ", weatherDataForecast=" + weatherDataForecast +
                ", date=" + date +
                ", location='" + location + '\'' +
                '}';
    }
}
