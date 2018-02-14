package acodexm.cleanweather.util;


import acodexm.cleanweather.R;

public class WeatherUtils {

    //convert API icon to corresponding in app vector assets
    public static int convertIconToResource(String icon) {
        switch (correctError(icon)) {
            case "01d":
                return R.drawable.ic_sun;
            case "01n":
                return R.drawable.ic_moon;
            case "02d":
                return R.drawable.ic_sun_cloudy;
            case "02n":
                return R.drawable.ic_moon_cloud;
            case "03d":
            case "03n":
            case "04d":
            case "04n":
                return R.drawable.ic_cloud;
            case "09d":
            case "09n":
            case "10d":
            case "10n":
                return R.drawable.ic_rain;
            case "11d":
            case "11n":
                return R.drawable.ic_storm;
            case "13d":
            case "13n":
                return R.drawable.ic_snow;
            default:
                return 0;
        }
    }

    public static int convertIconToBackground(String icon) {
        switch (correctError(icon)) {
            case "01d":
                return R.color.colorSunny;
            case "01n":
                return R.color.colorNight;
            case "02d":
                return R.color.colorSunny;
            case "02n":
                return R.color.colorNight;
            case "03d":
            case "03n":
            case "04d":
            case "04n":
                return R.color.colorCloudy;
            case "09d":
            case "09n":
            case "10d":
            case "10n":
                return R.color.colorRain;
            case "11d":
            case "11n":
                return R.color.colorRain;
            case "13d":
            case "13n":
                return R.color.colorSnow;
            default:
                return 0;
        }
    }

    private static String correctError(String icon) {
        if (icon.length() > 3) {
            return icon.substring(0, 3);
        }
        return icon;
    }

    public static String degreesToCardinal(double degrees) {
        String[] cardinals = {"N", "NE", "E", "SE", "S", "SW", "W", "NW", "N"};
        return cardinals[(int) Math.round((degrees % 360) / 45)];
    }
}
