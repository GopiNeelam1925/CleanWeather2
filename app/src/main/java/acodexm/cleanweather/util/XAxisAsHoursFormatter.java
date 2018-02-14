package acodexm.cleanweather.util;


import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

import acodexm.cleanweather.data.model.current.WeatherDataCurrent;


public class XAxisAsHoursFormatter implements IAxisValueFormatter {

    private WeatherDataCurrent mWeatherDataCurrent;
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("HH:mm");

    public XAxisAsHoursFormatter(WeatherDataCurrent weatherDataCurrent) {
        this.mWeatherDataCurrent = weatherDataCurrent;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        Date date = new Date(mWeatherDataCurrent.getCurrent().getLastUpdated());
        return mSimpleDateFormat.format(date);
    }
}