package acodexm.cleanweather.util;


import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

import acodexm.cleanweather.model.openweathermap.hourly.WeatherDataHourly;

public class XAxisAsHoursFormatter implements IAxisValueFormatter {

    private WeatherDataHourly mWeatherDataHourly;
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("HH:mm");

    public XAxisAsHoursFormatter(WeatherDataHourly weatherDataHourly) {
        this.mWeatherDataHourly = weatherDataHourly;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        Date date = new Date(mWeatherDataHourly.getList().get((int) value).getDt() * 1000L);
        return mSimpleDateFormat.format(date);
    }
}