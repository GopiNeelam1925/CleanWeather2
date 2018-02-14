package acodexm.cleanweather.view.fragments;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;

import acodexm.cleanweather.R;
import acodexm.cleanweather.model.openweathermap.daily.WeatherDataDaily;
import acodexm.cleanweather.util.Constants;
import acodexm.cleanweather.util.DataValueFormatter;
import acodexm.cleanweather.util.WeatherUtils;
import acodexm.cleanweather.util.XAxisAsDaysFormatter;
import acodexm.cleanweather.util.YAxisUnitFormatter;
import acodexm.cleanweather.view.activities.HomeActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyGraphFragment extends Fragment {
    private static final String TAG = DailyGraphFragment.class.getSimpleName();
    @BindView(R.id.forecast_text)
    protected TextView mForecastText;
    @BindView(R.id.forecast_chart_temp)
    protected LineChart mChartTemp;
    @BindView(R.id.forecast_chart_humidity)
    protected BarChart mChartHum;
    @BindView(R.id.forecast_chart_pressure)
    protected LineChart mChartPres;
    @BindView(R.id.forecast_chart_rain_chance)
    protected BarChart mChartRain;
    @BindView(R.id.forecast_chart_wind)
    protected LineChart mChartWind;
    @BindView(R.id.forecast_fragment_background)
    protected LinearLayout mLinearLayout;
    @BindView(R.id.forecast_swipe_to_refresh)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    private WeatherDataDaily mWeatherDataDaily;
    private SharedPreferences mPreferences;
    private boolean isCelsius;
    private int days;

    public DailyGraphFragment() {
    }

    public static DailyGraphFragment newInstance(WeatherDataDaily weatherDataDaily) {
        Log.d(TAG, "newInstance: "+weatherDataDaily.toString());
        DailyGraphFragment fragment = new DailyGraphFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.WEATHER_DATA, weatherDataDaily);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            mWeatherDataDaily = (WeatherDataDaily) getArguments().getSerializable(Constants.WEATHER_DATA);
        }
        View rootView = inflater.inflate(R.layout.fragment_weather_daily_graph, container, false);
        ButterKnife.bind(this, rootView);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        days = Integer.valueOf(mPreferences.getString(Constants.SETTING_DAY_LIST, 7 + ""));
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.blue, R.color.green);
        mSwipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(this::updateWeather, 1000));
        isCelsius = mPreferences.getBoolean(Constants.SETTING_TEMP_UNIT, false);
        if (mWeatherDataDaily != null)
            setupWeather(mWeatherDataDaily);

        setChartSettings();
        return rootView;
    }

    public void setupWeather(WeatherDataDaily weatherDataDaily) {
        mForecastText.setText(getResources().getString(R.string.forecast_sentence_start)
                + " " + days + " " + getString(R.string.forecast_sentence_end));
        mLinearLayout.setBackgroundColor(getResources().getColor(WeatherUtils.convertIconToBackground(weatherDataDaily.getList().get(0).getWeather().get(0).getIcon())));
    }

    public void updateWeather() {
        ((HomeActivity) getActivity()).updateWeather();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void setChartSettings() {

        setLineChartView(Constants.CHART_TEMP, mChartTemp, mWeatherDataDaily, days, isCelsius, null);
        setBarChartView(Constants.CHART_RAIN, mChartRain, mWeatherDataDaily, days, "mm");
        setLineChartView(Constants.CHART_WIND, mChartWind, mWeatherDataDaily, days, isCelsius, "m/s");
        setLineChartView(Constants.CHART_PRES, mChartPres, mWeatherDataDaily, days, isCelsius, "mb");
        setBarChartView(Constants.CHART_HUM, mChartHum, mWeatherDataDaily, days, "%");

    }

    private void setLineChartView(String chartType, LineChart chart, WeatherDataDaily weatherData, int days, boolean isCelsius, String unit) {
        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        XAxis xAxis = chart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new XAxisAsDaysFormatter(weatherData));
        xAxis.setTextSize(10f);
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line


        YAxis yAxis = chart.getAxisLeft();
        yAxis.enableGridDashedLine(10f, 10f, 0f);
        yAxis.setDrawZeroLine(false);
        yAxis.setValueFormatter(new YAxisUnitFormatter(isCelsius, unit));
        yAxis.setTextSize(15f);

        chart.getAxisRight().setEnabled(false);
        chart.getLegend().setEnabled(false);
        //enable animation

        chart.zoom(zoomXAxis(), 0, 0, 0);
        switch (chartType) {
            case Constants.CHART_TEMP:
                chart.setData(generateDataLine(weatherData, days, chartType));
            case Constants.CHART_WIND:
                chart.setData(generateDataLine(weatherData, days, chartType));
            case Constants.CHART_PRES:
                chart.setData(generateDataLine(weatherData, days, chartType));
        }
        chart.invalidate();
    }

    private LineData generateDataLine(WeatherDataDaily weatherDataDaily, int days, String chartType) {

        ArrayList<Entry> values = new ArrayList<>();
        switch (chartType) {
            case Constants.CHART_TEMP:
                for (int i = 0; i < days; i++) {
                    values.add(new Entry(i, weatherDataDaily.getList().get(i).getTemp().getDay().floatValue()));
                }
                break;
            case Constants.CHART_WIND:
                for (int i = 0; i < days; i++) {
                    values.add(new Entry(i, weatherDataDaily.getList().get(i).getSpeed().floatValue()));
                }
                break;
            case Constants.CHART_PRES:
                for (int i = 0; i < days; i++) {
                    values.add(new Entry(i, weatherDataDaily.getList().get(i).getPressure().floatValue()));
                }
                break;
        }
        Collections.sort(values, new EntryXComparator());

        LineDataSet dataSet = new LineDataSet(values, chartType);
        dataSet.setLineWidth(3f);
        dataSet.setCircleRadius(4f);
        dataSet.setCircleColor(Color.RED);
        dataSet.setColor(Color.YELLOW);
        dataSet.setValueTextSize(15f);
        dataSet.setDrawValues(true);
        dataSet.setDrawFilled(true);
        dataSet.setHighlightEnabled(false);
        switch (chartType) {
            case Constants.CHART_TEMP:
                dataSet.setValueFormatter(new DataValueFormatter(""));
                break;
            case Constants.CHART_WIND:
                dataSet.setValueFormatter(new DataValueFormatter(weatherDataDaily));
                break;
            case Constants.CHART_PRES:
                dataSet.setValueFormatter(new DataValueFormatter(""));
                break;
        }
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fill_color);
            dataSet.setFillDrawable(drawable);
        } else {
            dataSet.setFillColor(R.color.colorPrimary);
        }

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(dataSet);

        return new LineData(sets);
    }

    private void setBarChartView(String chartType, BarChart chart, WeatherDataDaily weatherData, int days, String unit) {
        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        XAxis xAxis = chart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new XAxisAsDaysFormatter(weatherData));
        xAxis.setTextSize(10f);
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line


        YAxis yAxis = chart.getAxisLeft();
        yAxis.enableGridDashedLine(10f, 10f, 0f);
        yAxis.setDrawZeroLine(false);
        yAxis.setValueFormatter(new YAxisUnitFormatter(false, unit));
        yAxis.setTextSize(15f);

        chart.getAxisRight().setEnabled(false);
        chart.getLegend().setEnabled(false);
        //enable animation

        chart.zoom(zoomXAxis(), 0, 0, 0);
        switch (chartType) {
            case Constants.CHART_RAIN:
                chart.setData(generateDataBar(weatherData, days, chartType));
            case Constants.CHART_HUM:
                chart.setData(generateDataBar(weatherData, days, chartType));
        }
        chart.invalidate();
    }

    private BarData generateDataBar(WeatherDataDaily weatherDataDaily, int days, String chartType) {

        ArrayList<BarEntry> entries = new ArrayList<>();
        switch (chartType) {
            case Constants.CHART_RAIN:
                for (int i = 0; i < days; i++) {
                    if (weatherDataDaily.getList().get(i).getRain() == null)
                        entries.add(new BarEntry(i, 0));
                    else
                        entries.add(new BarEntry(i, weatherDataDaily.getList().get(i).getRain().floatValue()));
                }
                break;
            case Constants.CHART_HUM:
                for (int i = 0; i < days; i++) {
                    entries.add(new BarEntry(i, weatherDataDaily.getList().get(i).getHumidity().floatValue()));
                }
                break;
        }
        Collections.sort(entries, new EntryXComparator());
        BarDataSet dataSet = new BarDataSet(entries, chartType);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        dataSet.setHighlightEnabled(false);
        dataSet.setValueTextSize(15f);
        switch (chartType) {
            case Constants.CHART_RAIN:
                dataSet.setValueFormatter(new DataValueFormatter(""));
                break;
            case Constants.CHART_HUM:
                dataSet.setValueFormatter(new DataValueFormatter(""));
                break;
        }
        BarData cd = new BarData(dataSet);
        cd.setBarWidth(0.9f);
        return cd;
    }


    private float zoomXAxis() {
        switch (mWeatherDataDaily.getList().size()) {
            case 1:
                return 1f;
            case 2:
                return 1f;
            case 3:
                return 1f;
            case 4:
                return 1f;
            case 5:
                return 1f;
            case 6:
                return 1f;
            case 7:
                return 1f;
            case 8:
                return 1.2f;
            case 9:
                return 1.3f;
            case 10:
                return 1.4f;
            case 11:
                return 1.5f;
            case 12:
                return 1.6f;
            case 13:
                return 1.7f;
            case 14:
                return 1.8f;
            case 15:
                return 1.9f;
            case 16:
                return 2f;
        }
        return 1f;
    }

}