package acodexm.cleanweather.util;


import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

import acodexm.cleanweather.model.openweathermap.daily.WeatherDataDaily;

public class XAxisAsDaysFormatter implements IAxisValueFormatter {

    private WeatherDataDaily mWeatherDataDaily;
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("EEE");

    public XAxisAsDaysFormatter(WeatherDataDaily weatherDataDaily) {
        this.mWeatherDataDaily = weatherDataDaily;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        Date date = new Date(mWeatherDataDaily.getList().get((int) value).getDt() * 1000L);
        return mSimpleDateFormat.format(date);
    }
}