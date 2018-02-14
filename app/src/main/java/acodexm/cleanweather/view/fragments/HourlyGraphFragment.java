package acodexm.cleanweather.view.fragments;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import acodexm.cleanweather.R;
import acodexm.cleanweather.model.openweathermap.daily.WeatherDataDaily;
import acodexm.cleanweather.model.openweathermap.hourly.WeatherDataHourly;
import acodexm.cleanweather.util.Constants;
import acodexm.cleanweather.util.DataValueFormatter;
import acodexm.cleanweather.util.WeatherUtils;
import acodexm.cleanweather.util.XAxisAsHoursFormatter;
import acodexm.cleanweather.util.YAxisUnitFormatter;
import acodexm.cleanweather.view.activities.HomeActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HourlyGraphFragment extends Fragment {
    private static final String TAG = HourlyGraphFragment.class.getSimpleName();
    @BindView(R.id.current_chart_temp)
    protected LineChart mChartTemp;
    @BindView(R.id.current_chart_humidity)
    protected BarChart mChartHum;
    @BindView(R.id.current_chart_pressure)
    protected LineChart mChartPres;
    @BindView(R.id.current_chart_rain_chance)
    protected BarChart mChartRain;
    @BindView(R.id.current_chart_wind)
    protected LineChart mChartWind;
    @BindView(R.id.current_swipe_to_refresh)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.current_fragment_background)
    protected LinearLayout mLinearLayout;
    @BindView(R.id.current_ic_weather)
    protected AppCompatImageView mImageWeather;
    @BindView(R.id.current_ic_clock)
    protected AppCompatImageView mImageClock;
    @BindView(R.id.current_ic_date)
    protected AppCompatImageView mImageDate;
    @BindView(R.id.current_text_date)
    protected TextView mDate;
    @BindView(R.id.current_text_time)
    protected TextView mTime;
    @BindView(R.id.current_text_city)
    protected TextView mLocation;
    private WeatherDataDaily mWeatherDataDaily;
    private WeatherDataHourly mWeatherDataHourly;
    private SharedPreferences mPreferences;
    private boolean isCelsius;

    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("MMM dd");
    private SimpleDateFormat mSimpleTimeFormat = new SimpleDateFormat("HH:mm");

    public HourlyGraphFragment() {
    }

    public static HourlyGraphFragment newInstance(WeatherDataHourly weatherDataHourly, WeatherDataDaily weatherDataDaily) {
        Log.d(TAG, "newInstance: "+weatherDataHourly.toString());
        Log.d(TAG, "newInstance: "+weatherDataDaily.toString());
        HourlyGraphFragment fragment = new HourlyGraphFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.WEATHER_DATA_HOURLY, weatherDataHourly);
        args.putSerializable(Constants.WEATHER_DATA, weatherDataDaily);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            mWeatherDataHourly = (WeatherDataHourly) getArguments().getSerializable(Constants.WEATHER_DATA_HOURLY);
            mWeatherDataDaily = (WeatherDataDaily) getArguments().getSerializable(Constants.WEATHER_DATA);
        }
        View rootView = inflater.inflate(R.layout.fragment_weather_hourly_graph, container, false);
        ButterKnife.bind(this, rootView);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.blue, R.color.green);
        mSwipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(this::updateWeather, 1000));
        isCelsius = mPreferences.getBoolean(Constants.SETTING_TEMP_UNIT, false);
        if (mWeatherDataDaily != null)
            setupWeather(mWeatherDataDaily);
        setChartSettings();
        return rootView;
    }

    @OnClick(R.id.current_ic_date)
    public void onDateClick() {
        //todo other devices calendars
        vibrate(50);
        startActivity(new Intent().setComponent(new ComponentName("com.google.android.calendar", "com.android.calendar.LaunchActivity")));
    }

    @OnClick(R.id.current_ic_clock)
    public void onClockClick() {
        //todo other devices clocks
        vibrate(50);
        startActivity(new Intent().setComponent(new ComponentName("com.google.android.deskclock", "com.android.deskclock.DeskClock")));
    }

    public void updateWeather() {
        ((HomeActivity) getActivity()).updateWeather();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void vibrate(long milliseconds) {
        Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(milliseconds);
    }

    public void setupWeather(WeatherDataDaily weatherDataDaily) {
        int position = 0;
        mLocation.setText(weatherDataDaily.getCity().getName());
        mImageWeather.setImageResource(WeatherUtils.convertIconToResource(weatherDataDaily.getList().get(position).getWeather().get(0).getIcon()));
        mLinearLayout.setBackgroundColor(getResources().getColor(WeatherUtils.convertIconToBackground(weatherDataDaily.getList().get(position).getWeather().get(0).getIcon())));
        Date date = new Date(weatherDataDaily.getList().get(position).getDt() * 1000L);
        mDate.setText(mSimpleDateFormat.format(date));
        mTime.setText(mSimpleTimeFormat.format(new Date()));
        Log.d("Fragment setup", "Completed");

    }

    public void setChartSettings() {
        setLineChartView(Constants.CHART_TEMP, mChartTemp, mWeatherDataHourly, isCelsius, null);
        setBarChartView(Constants.CHART_RAIN, mChartRain, mWeatherDataHourly, "mm");
        setLineChartView(Constants.CHART_WIND, mChartWind, mWeatherDataHourly, isCelsius, "m/s");
        setLineChartView(Constants.CHART_PRES, mChartPres, mWeatherDataHourly, isCelsius, "mb");
        setBarChartView(Constants.CHART_HUM, mChartHum, mWeatherDataHourly, "%");

    }

    private void setLineChartView(String chartType, LineChart chart, WeatherDataHourly weatherData, boolean isCelsius, String unit) {
        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        XAxis xAxis = chart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new XAxisAsHoursFormatter(weatherData));
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

        chart.zoom(4.8f, 0, 0, 0);
        switch (chartType) {
            case Constants.CHART_TEMP:
                chart.setData(generateDataLine(weatherData, chartType));
            case Constants.CHART_WIND:
                chart.setData(generateDataLine(weatherData, chartType));
            case Constants.CHART_PRES:
                chart.setData(generateDataLine(weatherData, chartType));
        }
        chart.invalidate();
    }

    private LineData generateDataLine(WeatherDataHourly weatherDataHourly, String chartType) {

        ArrayList<Entry> values = new ArrayList<>();
        switch (chartType) {
            case Constants.CHART_TEMP:
                for (int i = 0; i < weatherDataHourly.getList().size(); i++) {
                    values.add(new Entry(i, weatherDataHourly.getList().get(i).getMain().getTemp().floatValue()));
                }
                break;
            case Constants.CHART_WIND:
                for (int i = 0; i < weatherDataHourly.getList().size(); i++) {
                    values.add(new Entry(i, weatherDataHourly.getList().get(i).getWind().getSpeed().floatValue()));
                }
                break;
            case Constants.CHART_PRES:
                for (int i = 0; i < weatherDataHourly.getList().size(); i++) {
                    values.add(new Entry(i, weatherDataHourly.getList().get(i).getMain().getPressure().floatValue()));
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
                dataSet.setValueFormatter(new DataValueFormatter(weatherDataHourly));
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

    private void setBarChartView(String chartType, BarChart chart, WeatherDataHourly weatherData, String unit) {
        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        XAxis xAxis = chart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new XAxisAsHoursFormatter(weatherData));
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

        chart.zoom(4.8f, 0, 0, 0);
        switch (chartType) {
            case Constants.CHART_RAIN:
                chart.setData(generateDataBar(weatherData, chartType));
            case Constants.CHART_HUM:
                chart.setData(generateDataBar(weatherData, chartType));
        }
        chart.invalidate();
    }

    private BarData generateDataBar(WeatherDataHourly weatherDataHourly, String chartType) {

        ArrayList<BarEntry> entries = new ArrayList<>();
        switch (chartType) {
            case Constants.CHART_RAIN:
                for (int i = 0; i < weatherDataHourly.getList().size(); i++) {
                    if (weatherDataHourly.getList().get(i).getRain() == null || weatherDataHourly.getList().get(i).getRain().get3h() == null)
                        entries.add(new BarEntry(i, 0));
                    else
                        entries.add(new BarEntry(i, weatherDataHourly.getList().get(i).getRain().get3h().floatValue()));
                }
                break;
            case Constants.CHART_HUM:
                for (int i = 0; i < weatherDataHourly.getList().size(); i++) {
                    entries.add(new BarEntry(i, weatherDataHourly.getList().get(i).getMain().getHumidity().floatValue()));
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

}