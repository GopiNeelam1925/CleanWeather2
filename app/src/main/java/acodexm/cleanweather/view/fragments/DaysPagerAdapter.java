package acodexm.cleanweather.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import acodexm.cleanweather.R;
import acodexm.cleanweather.util.Constants;

public class DaysPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private Context mContext;

    public DaysPagerAdapter(FragmentManager fm, List<Fragment> fragments, Context context) {
        super(fm);
        mFragments = fragments;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.page_one);
            case 1:
                return mContext.getString(R.string.page_two);
            case 2:
                SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
                return mContext.getString(R.string.page_tree_start)
                        + " " + Integer.valueOf(mSharedPreferences.getString(Constants.SETTING_DAY_LIST, 7 + ""))
                        + " " + mContext.getString(R.string.page_tree_end);
        }
        return "";
    }
}