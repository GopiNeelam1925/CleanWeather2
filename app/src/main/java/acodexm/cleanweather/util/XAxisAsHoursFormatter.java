package acodexm.cleanweather.util;


import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

import acodexm.cleanweather.data.model.forecast.Current;


public class XAxisAsHoursFormatter implements IAxisValueFormatter {

    private Current current;
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("HH:mm");

    public XAxisAsHoursFormatter(Current current) {
        this.current = current;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        Date date = new Date(current.getLastUpdatedEpoch());
        return mSimpleDateFormat.format(date);
    }
}