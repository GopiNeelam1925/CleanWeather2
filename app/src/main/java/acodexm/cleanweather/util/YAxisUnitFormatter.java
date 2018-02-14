package acodexm.cleanweather.util;


import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

public class YAxisUnitFormatter implements IAxisValueFormatter {

    private boolean isCelsius;
    private String unit;
    private DecimalFormat mFormat;

    public YAxisUnitFormatter(boolean isCelsius, String unit) {
        mFormat = new DecimalFormat("####.#");
        this.isCelsius = isCelsius;
        this.unit = unit;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if (unit != null)
            return mFormat.format(value) + unit;
        else
            return isCelsius ? mFormat.format(value) + "\u00b0F" : mFormat.format(value) + "\u00b0C";
    }
}