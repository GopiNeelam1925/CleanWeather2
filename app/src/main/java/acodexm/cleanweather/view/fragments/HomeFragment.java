package acodexm.cleanweather.view.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
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
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.inject.Inject;

import acodexm.cleanweather.R;
import acodexm.cleanweather.data.model.WeatherData;
import acodexm.cleanweather.data.model.forecast.Day;
import acodexm.cleanweather.injection.Injectable;
import acodexm.cleanweather.util.Constants;
import acodexm.cleanweather.util.DataValueFormatter;
import acodexm.cleanweather.util.WeatherUtils;
import acodexm.cleanweather.util.XAxisAsDaysFormatter;
import acodexm.cleanweather.util.XAxisAsHoursFormatter;
import acodexm.cleanweather.view.activities.HomeActivity;
import acodexm.cleanweather.view.viewmodel.WeatherDataViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class HomeFragment extends Fragment implements Injectable {
    private static final String TAG = HomeFragment.class.getSimpleName();
    @BindView(R.id.text_city)
    protected TextView mLocation;
    @BindView(R.id.text_desc)
    protected TextView mWeatherCondition;
    @BindView(R.id.text_date)
    protected TextView mDate;
    @BindView(R.id.text_time)
    protected TextView mTime;
    @BindView(R.id.text_temp_main)
    protected TextView mTemp;
    @BindView(R.id.text_temp_unit)
    protected TextView mTempUnit;
    @BindView(R.id.text_temp_min)
    protected TextView mTempMin;
    @BindView(R.id.fragment_background)
    protected LinearLayout mLinearLayout;
    @BindView(R.id.ic_weather)
    protected AppCompatImageView mImageWeather;
    @BindView(R.id.ic_clock)
    protected AppCompatImageView mImageClock;
    @BindView(R.id.ic_date)
    protected AppCompatImageView mImageDate;
    @BindView(R.id.swipe_to_refresh)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.main_chart_icons)
    protected LineChart mChartIconsDaily;
    @BindView(R.id.main_chart_icons_hourly)
    protected LineChart mChartIconsHourly;
    private WeatherData mWeatherData;
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("MMM dd");
    private SimpleDateFormat mSimpleTimeFormat = new SimpleDateFormat("HH:mm");
    private SharedPreferences mPreferences;
    private int days;
    private boolean isCelsius;
    private WeatherDataViewModel dataViewModel;
    @Inject
    ViewModelProvider.Factory modelFactory;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(int sectionNumber, WeatherData weatherData) {
        Timber.d("newInstance: %s", weatherData.toString());
        return new HomeFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, rootView);
        dataViewModel = ViewModelProviders.of(this, modelFactory).get(WeatherDataViewModel.class);
        mWeatherData = dataViewModel.getWeatherData().getValue();
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        days = Integer.valueOf(mPreferences.getString(Constants.SETTING_DAY_LIST, 7 + ""));
        isCelsius = mPreferences.getBoolean(Constants.SETTING_TEMP_UNIT, false);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.blue, R.color.green);
        mSwipeRefreshLayout.setOnRefreshListener(()
                -> new Handler().postDelayed(this::updateWeather, 1000));
        boolean isCelsius = mPreferences.getBoolean(Constants.SETTING_TEMP_UNIT, false);
        if (mWeatherData != null) {
            Timber.d(TAG, "onCreateView: mWeatherData" + mWeatherData.toString());
            setChartSettings();
            setupWeather(mWeatherData, isCelsius);
        }
        return rootView;
    }

    @OnClick(R.id.ic_date)
    public void onDateClick() {
        //todo other devices calendars
        vibrate(50);
        startActivity(new Intent().setComponent(new ComponentName("com.google.android.calendar",
                "com.android.calendar.LaunchActivity")));
    }

    @OnClick(R.id.ic_clock)
    public void onClockClick() {
        //todo other devices clocks
        vibrate(50);
        startActivity(new Intent().setComponent(new ComponentName("com.google.android.deskclock",
                "com.android.deskclock.DeskClock")));
    }

    public void updateWeather() {
        ((HomeActivity) getActivity()).updateWeather();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void vibrate(long milliseconds) {
        Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(milliseconds);
    }


    public void setupWeather(WeatherData weatherData, boolean isCelsius) {
        int position = 0;
        Day day = weatherData.getWeatherDataForecast().getForecast().getForecastday().get(position).getDay();
        mTemp.setText(day.getAvgtempC().toString());
        mTempMin.setText("|" + day.getMintempC().toString());
        mTempUnit.setText(isCelsius ? "\u00b0F" : "\u00b0C");
        mLocation.setText(weatherData.getLocationName());
        mWeatherCondition.setText(
                day.getCondition().getText());
        mImageWeather.setImageResource(WeatherUtils.convertIconToResource(
                day.getCondition().getIcon()));
        mLinearLayout.setBackgroundColor(getResources().getColor(WeatherUtils.convertIconToBackground(
                day.getCondition().getIcon())));
        Date date = new Date(weatherData.getWeatherDataCurrent().getCurrentCurrent().getLastUpdatedCurrent());
        mDate.setText(mSimpleDateFormat.format(date));
        mTime.setText(mSimpleTimeFormat.format(new Date()));
        Timber.d("Fragment setup Completed");

    }

    public void setChartSettings() {
        setLineChartView(Constants.CHART_DAYS, mChartIconsDaily, mWeatherData, days, isCelsius, null);
        setLineChartView(Constants.CHART_HOURS, mChartIconsHourly, mWeatherData, days, isCelsius, null);
    }

    private void setLineChartView(String chartType, LineChart chart, WeatherData weatherData, int days, boolean isCelsius, String unit) {
        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        XAxis xAxis = chart.getXAxis();
        xAxis.disableGridDashedLine();
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line


        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        chart.getLegend().setEnabled(false);
        //enable animation

        switch (chartType) {
            case Constants.CHART_DAYS:
                xAxis.setValueFormatter(new XAxisAsDaysFormatter(weatherData.getWeatherDataForecast()));
                chart.zoom(zoomXAxis(), 0, 0, 0);
                chart.setData(generateDataLine(weatherData, days, chartType));
                break;
            case Constants.CHART_HOURS:
                xAxis.setValueFormatter(new XAxisAsHoursFormatter(weatherData.getWeatherDataCurrent()));
                chart.zoom(4.8f, 0, 0, 0);
                chart.setData(generateDataLine(weatherData, days, chartType));
                break;

        }
        chart.invalidate();
    }

    private LineData generateDataLine(WeatherData weatherData, int days, String chartType) {

        ArrayList<Entry> values = new ArrayList<>();
        switch (chartType) {
            case Constants.CHART_DAYS:
//                for (int i = 0; i < days; i++) {
//                    values.add(new Entry(i, weatherData.getWeatherDataForecast().getForecast().getForecastday().get(i).getDay().getAvgtempC(),
//                            getResources().getDrawable(WeatherUtils.convertIconToResource(
//                                    weatherData.getWeatherDataForecast().getForecast().getForecastday().get(i).getDay().getConditionCurrentCurrent().getIconCurrent()))));
//                }
                break;
            case Constants.CHART_HOURS:
//                for (int i = 0; i < weatherData.getWeatherDataHourly().getList().size(); i++) {
//                    values.add(new Entry(i, weatherData.getWeatherDataHourly().getList().get(i).getMain().getTemp().floatValue(),
//                            getResources().getDrawable(WeatherUtils.convertIconToResource(
//                                    weatherData.getWeatherDataHourly().getList().get(i).getWeather().get(0).getIconCurrent()))));
//                }
                break;

        }
        Collections.sort(values, new EntryXComparator());

        LineDataSet dataSet = new LineDataSet(values, chartType);
        dataSet.setLineWidth(1f);
        dataSet.enableDashedLine(10f, 25f, 0f);
        dataSet.setColor(Color.DKGRAY);
        dataSet.setIconsOffset(new MPPointF(0, -35f));
        dataSet.setDrawIcons(true);
        dataSet.setValueTextSize(15f);
        dataSet.setDrawValues(true);
        dataSet.setDrawFilled(true);
        dataSet.setHighlightEnabled(false);
        switch (chartType) {
            case Constants.CHART_DAYS:
                dataSet.setValueFormatter(new DataValueFormatter(isCelsius ? "\u00b0F" : "\u00b0C"));
                break;
        }
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fill_color_red);
            dataSet.setFillDrawable(drawable);
        } else {
            dataSet.setFillColor(R.color.colorPrimary);
        }

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(dataSet);

        return new LineData(sets);
    }

    private float zoomXAxis() {
        switch (mWeatherData.getWeatherDataForecast().getForecast().getForecastday().size()) {
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