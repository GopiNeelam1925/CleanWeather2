package acodexm.cleanweather.util;


import acodexm.cleanweather.R;

public enum WeatherIcon {
    SUNNY(R.drawable.ic_sun, R.color.colorSunny),
    MOSTLY_SUNNY(R.drawable.ic_sun_cloudy, R.color.colorCloudy),
    CLEAR(R.drawable.ic_sun, R.color.colorSunny),
    CLOUDY(R.drawable.ic_cloud, R.color.colorCloudy),
    RAIN(R.drawable.ic_rain, R.color.colorRain),
    SNOW(R.drawable.ic_snow, R.color.colorSnow),
    THUNDERSTORM(R.drawable.ic_storm, R.color.colorRain);

    private int mResource;
    private int mColor;

    WeatherIcon(int resource, int color) {
        mResource = resource;
        mColor = color;
    }

    public int getColor() {
        return mColor;
    }

    public int getResource() {
        return mResource;
    }
}
