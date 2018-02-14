package acodexm.cleanweather.util;


import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

import acodexm.cleanweather.data.model.forecast.WeatherDataForecast;


public class XAxisAsDaysFormatter implements IAxisValueFormatter {

    private WeatherDataForecast mWeatherDataForecast;
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("EEE");

    public XAxisAsDaysFormatter(WeatherDataForecast weatherDataForecast) {
        this.mWeatherDataForecast = weatherDataForecast;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        Date date = new Date(mWeatherDataForecast.getForecast().getForecastday().get((int) value).getDate());
        return mSimpleDateFormat.format(date);
    }
}