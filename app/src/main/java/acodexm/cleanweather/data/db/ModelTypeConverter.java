package acodexm.cleanweather.data.db;


import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import acodexm.cleanweather.data.model.forecast.ForecastDay;

public class ModelTypeConverter {

    @TypeConverter
    public static LocalDateTime toDate(Long timestamp) {
        return timestamp == null ? null : LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofTotalSeconds(0));
    }

    @TypeConverter
    public static Long toTimestamp(LocalDateTime date) {
        return date == null ? null : date.toInstant(ZoneOffset.ofTotalSeconds(0)).getEpochSecond();
    }

    @TypeConverter
    public static List<ForecastDay> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<ForecastDay>>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<ForecastDay> forecastDays) {
        Gson gson = new Gson();
        return gson.toJson(forecastDays);
    }
//
//    @TypeConverter
//    public static Object stringToSomeObject(String data) {
//        if (data == null) {
//            return Collections.emptyList();
//        }
//        Gson gson = new Gson();
//        Type listType = new TypeToken<Object>() {
//        }.getType();
//        return gson.fromJson(data, listType);
//    }
//
//    @TypeConverter
//    public static String someObjectToString(Object someObjects) {
//        Gson gson = new Gson();
//        return gson.toJson(someObjects);
//    }
}

