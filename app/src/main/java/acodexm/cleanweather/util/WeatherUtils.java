package acodexm.cleanweather.util;


public class WeatherUtils {
    public static WeatherCondition convertCodeToResource(int code) {
        switch (code) {
            case 1000:
                return WeatherCondition.SUNNY;
            case 1003:
                return WeatherCondition.PARTLY_CLOUDY;
            case 1006:
                return WeatherCondition.CLOUDY;
            case 1009:
                return WeatherCondition.OVERCAST;
            case 1030:
                return WeatherCondition.MIST;
            case 1063:
                return WeatherCondition.CHANCE_OF_RAIN;
            case 1066:
                return WeatherCondition.CHANCE_OF_SNOW;
            case 1069:
                return WeatherCondition.SLEET_SHOWERS;
            case 1072:
            case 1168:
            case 1171:
                return WeatherCondition.FREEZING_DRIZZLE;
            case 1087:
                return WeatherCondition.CHANCE_OF_THUNDERSTORM;
            case 1117:
                return WeatherCondition.BLIZZARD;
            case 1135:
            case 1147:
                return WeatherCondition.FOG;
            case 1150:
            case 1153:
                return WeatherCondition.LIGHT_DRIZZLE;
            case 1180:
            case 1183:
                return WeatherCondition.LIGHT_RAIN;
            case 1186:
            case 1189:
            case 1192:
            case 1195:
                return WeatherCondition.RAIN;
            case 1198:
            case 1201:
                return WeatherCondition.FREEZING_RAIN;
            case 1204:
            case 1207:
            case 1237:
            case 1261:
            case 1264:
                return WeatherCondition.SLEET;
            case 1210:
            case 1213:
                return WeatherCondition.LIGHT_SNOW;
            case 1114:
            case 1216:
            case 1219:
            case 1222:
            case 1225:
                return WeatherCondition.SNOW;
            case 1240:
                return WeatherCondition.SCATTERED_SHOWERS;
            case 1243:
                return WeatherCondition.SHOWERS;
            case 1246:
                return WeatherCondition.RAIN;
            case 1249:
            case 1252:
                return WeatherCondition.SLEET_SHOWERS;
            case 1255:
            case 1258:
                return WeatherCondition.SNOW_SHOWERS;
            case 1273:
                return WeatherCondition.STORM;
            case 1276:
                return WeatherCondition.THUNDERSTORMS;
            case 1279:
            case 1282:
                return WeatherCondition.SNOW_THUNDERSTORMS;

            default:
                return WeatherCondition.SUNNY;
        }
    }
//    public static int convertCodeToBackground(int code) {
//        switch (code) {
//            case 1000:
//                return WeatherCondition.SUNNY;
//            case 1003:
//                return WeatherCondition.PARTLY_CLOUDY;
//            case 1006:
//                return WeatherCondition.CLOUDY;
//            case 1009:
//                return WeatherCondition.OVERCAST;
//            case 1030:
//                return WeatherCondition.MIST;
//            case 1063:
//                return WeatherCondition.CHANCE_OF_RAIN;
//            case 1066:
//                return WeatherCondition.CHANCE_OF_SNOW;
//            case 1069:
//                return WeatherCondition.SLEET_SHOWERS;
//            case 1072:
//            case 1168:
//            case 1171:
//                return WeatherCondition.FREEZING_DRIZZLE;
//            case 1087:
//                return WeatherCondition.CHANCE_OF_THUNDERSTORM;
//            case 1117:
//                return WeatherCondition.BLIZZARD;
//            case 1135:
//            case 1147:
//                return WeatherCondition.FOG;
//            case 1150:
//            case 1153:
//                return WeatherCondition.LIGHT_DRIZZLE;
//            case 1180:
//            case 1183:
//                return WeatherCondition.LIGHT_RAIN;
//            case 1186:
//            case 1189:
//            case 1192:
//            case 1195:
//                return WeatherCondition.RAIN;
//            case 1198:
//            case 1201:
//                return WeatherCondition.FREEZING_RAIN;
//            case 1204:
//            case 1207:
//            case 1237:
//            case 1261:
//            case 1264:
//                return WeatherCondition.SLEET;
//            case 1210:
//            case 1213:
//                return WeatherCondition.LIGHT_SNOW;
//            case 1114:
//            case 1216:
//            case 1219:
//            case 1222:
//            case 1225:
//                return WeatherCondition.SNOW;
//            case 1240:
//                return WeatherCondition.SCATTERED_SHOWERS;
//            case 1243:
//                return WeatherCondition.SHOWERS;
//            case 1246:
//                return WeatherCondition.RAIN;
//            case 1249:
//            case 1252:
//                return WeatherCondition.SLEET_SHOWERS;
//            case 1255:
//            case 1258:
//                return WeatherCondition.SNOW_SHOWERS;
//            case 1273:
//                return WeatherCondition.STORM;
//            case 1276:
//                return  WeatherCondition.THUNDERSTORMS;
//            case 1279:
//            case 1282:
//                return WeatherCondition.SNOW_THUNDERSTORMS;
//
//            default:
//                return WeatherCondition.SUNNY;
//        }
//    }
//    //convert API icon to corresponding in app vector assets
//    public static int convertIconToResource(String icon) {
//        switch (correctError(icon)) {
//            case "01d":
//                return R.drawable.ic_sun;
//            case "01n":
//                return R.drawable.ic_moon;
//            case "02d":
//                return R.drawable.ic_sun_cloudy;
//            case "02n":
//                return R.drawable.ic_moon_cloud;
//            case "03d":
//            case "03n":
//            case "04d":
//            case "04n":
//                return R.drawable.ic_cloud;
//            case "09d":
//            case "09n":
//            case "10d":
//            case "10n":
//                return R.drawable.ic_rain;
//            case "11d":
//            case "11n":
//                return R.drawable.ic_storm;
//            case "13d":
//            case "13n":
//                return R.drawable.ic_snow;
//            default:
//                return 0;
//        }
//    }
//
//    public static int convertIconToBackground(String icon) {
//        switch (correctError(icon)) {
//            case "01d":
//                return R.color.colorSunny;
//            case "01n":
//                return R.color.colorNight;
//            case "02d":
//                return R.color.colorSunny;
//            case "02n":
//                return R.color.colorNight;
//            case "03d":
//            case "03n":
//            case "04d":
//            case "04n":
//                return R.color.colorCloudy;
//            case "09d":
//            case "09n":
//            case "10d":
//            case "10n":
//                return R.color.colorRain;
//            case "11d":
//            case "11n":
//                return R.color.colorRain;
//            case "13d":
//            case "13n":
//                return R.color.colorSnow;
//            default:
//                return 0;
//        }
//    }

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
