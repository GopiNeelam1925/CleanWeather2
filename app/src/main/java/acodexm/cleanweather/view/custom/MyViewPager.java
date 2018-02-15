package acodexm.cleanweather.view.custom;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import acodexm.cleanweather.R;
import acodexm.cleanweather.view.activities.HomeActivity;
import acodexm.cleanweather.view.fragments.HomeFragment;
import acodexm.cleanweather.view.fragments.HourlyGraphFragment;
import acodexm.cleanweather.view.fragments.WeatherForecastFragment;

public class MyViewPager extends ViewPager {

    /**
     * Reference to the launch activity
     */
    private HomeActivity mHomeActivity;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setHomeActivity(HomeActivity homeActivity) {
        mHomeActivity = homeActivity;
    }


    /**
     * Determines if the pager can be swiped based off the x and y inputs provided, as well as if the
     * barchart can be panned or not.
     *
     * @param x The x coordinate to check
     * @param y The y coordinate to check
     * @return True if the ViewPager will continue with normal swiping action.
     */
    public boolean canSwipe(float x, float y) {
        switch (mHomeActivity.getCurrentPage()) {
            case 0: {
                HomeFragment fragment = (HomeFragment) mHomeActivity.getDaysPagerAdapter().instantiateItem(this, 0);
                List<View> charts = new ArrayList<>();
                charts.add(fragment.getView().findViewById(R.id.main_chart_icons));
                charts.add(fragment.getView().findViewById(R.id.main_chart_icons_hourly));

                int i = 0;
                for (View view : charts) {
                    if (isPointInsideView(x, y, view)) i++;
                }
                return i == 0;
            }
            case 1: {
                HourlyGraphFragment fragment = (HourlyGraphFragment) mHomeActivity.getDaysPagerAdapter().instantiateItem(this, 1);
                List<View> charts = new ArrayList<>();
                charts.add(fragment.getView().findViewById(R.id.current_chart_humidity));
                charts.add(fragment.getView().findViewById(R.id.current_chart_pressure));
                charts.add(fragment.getView().findViewById(R.id.current_chart_rain_chance));
                charts.add(fragment.getView().findViewById(R.id.current_chart_temp));
                charts.add(fragment.getView().findViewById(R.id.current_chart_wind));
                int i = 0;
                for (View view : charts) {
                    if (isPointInsideView(x, y, view)) i++;
                }
                return i == 0;

            }
            case 2: {
                WeatherForecastFragment fragment = (WeatherForecastFragment) mHomeActivity.getDaysPagerAdapter().instantiateItem(this, 2);
//                List<View> charts = new ArrayList<>();
//                charts.add(fragment.getView().findViewById(R.id.forecast_chart_humidity));
//                charts.add(fragment.getView().findViewById(R.id.forecast_chart_pressure));
//                charts.add(fragment.getView().findViewById(R.id.forecast_chart_rain_chance));
//                charts.add(fragment.getView().findViewById(R.id.forecast_chart_temp));
//                charts.add(fragment.getView().findViewById(R.id.forecast_chart_wind));
//                int i = 0;
//                for (View view : charts) {
//                    if (isPointInsideView(x, y, view)) i++;
//                }
                return true;
            }
        }
        return true;
    }

    /**
     * Takes x and y coordinates and compares them to the coordinates of the passed view. Returns true if the passed coordinates
     * are within the range of the {@code view}
     *
     * @param x    The x coordinate to compare
     * @param y    The y coordinate to compare
     * @param view The view to check the coordinates of
     * @return True if the x and y coordinates match that of the view
     */
    private boolean isPointInsideView(float x, float y, View view) {
        int location[] = new int[2];
        view.getLocationInWindow(location);
        int viewX = location[0];
        int viewY = location[1];
        viewY = viewY - 320;
        return ((x > viewX && x < (viewX + view.getWidth())) && (y > viewY && y < (viewY + view.getHeight())));
    }

    /**
     * Override of the onInterceptTouchEvent which allows swiping to be disabled when chart is selected
     *
     * @param ev The MotionEvent object
     * @return Call to super if true, otherwise returns false
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return canSwipe(ev.getX(), ev.getY()) && super.onInterceptTouchEvent(ev);
    }

}
