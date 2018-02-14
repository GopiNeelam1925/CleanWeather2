package acodexm.cleanweather.view;


import acodexm.cleanweather.model.openweathermap.WeatherData;

public interface HomeView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void setWeatherView(WeatherData weatherData);


}
