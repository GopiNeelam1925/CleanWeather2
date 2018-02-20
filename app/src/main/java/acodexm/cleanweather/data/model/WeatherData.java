package acodexm.cleanweather.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.threeten.bp.LocalDateTime;

import acodexm.cleanweather.data.model.forecast.WeatherDataForecast;

import static acodexm.cleanweather.data.model.WeatherData.TABLE_NAME;


@Entity(tableName = TABLE_NAME)
public class WeatherData {

    public static final String TABLE_NAME = "weather_data";
    public static final String LOCATION_FIELD = "location";
    public static final String DATE_FIELD = "insert_date";


    @PrimaryKey
    @ColumnInfo(name = LOCATION_FIELD)
    @NonNull
    private String locationName = "";
    @Embedded
    private WeatherDataForecast weatherDataForecast;
    @ColumnInfo(name = DATE_FIELD)
    private LocalDateTime date;

    public WeatherData() {
    }

    @Ignore
    public WeatherData(WeatherDataForecast weatherDataForecast, LocalDateTime date) {
        this.weatherDataForecast = weatherDataForecast;
        this.date = date;
        this.locationName = weatherDataForecast.getLocation().getName().toLowerCase().trim();
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

    @NonNull
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(@NonNull String locationName) {
        this.locationName = locationName.toLowerCase().trim();
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                ", weatherDataForecast=" + weatherDataForecast +
                ", date=" + date +
                ", locationName='" + locationName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof WeatherData && ((WeatherData) obj).getLocationName().equals(this.getLocationName());
    }
}
