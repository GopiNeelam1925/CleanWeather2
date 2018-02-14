package acodexm.cleanweather.util;


import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

import acodexm.cleanweather.model.openweathermap.daily.WeatherDataDaily;
import acodexm.cleanweather.model.openweathermap.hourly.WeatherDataHourly;

import static acodexm.cleanweather.util.WeatherUtils.degreesToCardinal;

public class DataValueFormatter implements IValueFormatter {

    private DecimalFormat mFormat;
    private Object data;

    public DataValueFormatter(Object data) {
        mFormat = new DecimalFormat("####.###");
        this.data = data;
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        String directions = "";
        if (data instanceof WeatherDataDaily) {
            WeatherDataDaily dataDaily = (WeatherDataDaily) data;
            directions = degreesToCardinal(dataDaily.getList().get((int) entry.getX()).getDeg());
        } else if (data instanceof WeatherDataHourly) {
            WeatherDataHourly dataDaily = (WeatherDataHourly) data;
            directions = degreesToCardinal(dataDaily.getList().get((int) entry.getX()).getWind().getDeg());
        } else if (data instanceof String) {
            directions = (String) data;
        }
        return mFormat.format(value) + directions;
    }
}