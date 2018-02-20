package acodexm.cleanweather.view.viewmodel;


import acodexm.cleanweather.data.model.LocationData;
import acodexm.cleanweather.data.model.WeatherData;

public interface ModelViewControl {
    void deleteWeather(WeatherData weatherData);

    void deleteLocation(LocationData location);
}
