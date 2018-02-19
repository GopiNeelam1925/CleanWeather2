package acodexm.cleanweather.view.activities;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import acodexm.cleanweather.R;
import acodexm.cleanweather.gps.MyLocationListener;
import acodexm.cleanweather.injection.ViewModelFactory;
import acodexm.cleanweather.util.Constants;
import acodexm.cleanweather.view.custom.MyViewPager;
import acodexm.cleanweather.view.custom.ZoomOutPageTransformer;
import acodexm.cleanweather.view.fragments.DaysPagerAdapter;
import acodexm.cleanweather.view.fragments.SidebarAdapter;
import acodexm.cleanweather.view.fragments.WeatherCurrentFragment;
import acodexm.cleanweather.view.fragments.WeatherForecastFragment;
import acodexm.cleanweather.view.viewmodel.WeatherDataViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        SidebarAdapter.SidebarUserClickAction, HasSupportFragmentInjector {
    private static final int REQUEST_RUNTIME_PERMISSION = 123;
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.tabs)
    protected TabLayout mTabLayout;
    @BindView(R.id.container)
    protected MyViewPager mViewPager;
    @BindView(R.id.progress_container)
    protected ViewGroup progressContainer;
    @BindView(R.id.progress_message)
    protected TextView progressMessage;
    @BindView(R.id.error_container)
    protected ViewGroup errorContainer;
    @BindView(R.id.fabBtn)
    protected FloatingActionButton mActionButton;
    @BindView(R.id.navigation_drawer_list)
    protected RecyclerView mSidebarList;
    @BindView(R.id.add_city)
    protected TextView mAddCity;
    private SearchView searchView;
    private String geoLocation;
    private boolean isBackButtonPressed;
    private SharedPreferences mSharedPreferences;
    private SidebarAdapter mSidebarAdapter;
    private DaysPagerAdapter mDaysPagerAdapter;
    private WeatherDataViewModel dataViewModel;

    @Inject
    ViewModelFactory modelFactory;

    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().hide();
        dataViewModel = ViewModelProviders.of(this, modelFactory).get(WeatherDataViewModel.class);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        if (checkGPSPermission()) {
            geoLocation = getLocation();
        }
        mViewPager.setHomeActivity(this);
        mSidebarAdapter = new SidebarAdapter();
        mSidebarAdapter.setSidebarClickListener(this);
        mSidebarList.setLayoutManager(new LinearLayoutManager(this));
        mSidebarList.setAdapter(mSidebarAdapter);

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }

    public int getCurrentPage() {
        return mViewPager.getCurrentItem();

    }

    public DaysPagerAdapter getDaysPagerAdapter() {
        return mDaysPagerAdapter;
    }

    public void updateWeather() {
        Timber.d("updateWeather: ");
    }

    public int setAmountOfDays() {
        return Integer.valueOf(mSharedPreferences.getString(Constants.SETTING_DAY_LIST, 7 + ""));
    }

//    public String setUnits() {
//
//        if (mSharedPreferences.getBoolean(Constants.SETTING_TEMP_UNIT, false)) {
//            return "imperial";
//        } else {
//            return "metric";
//        }
//    }
//    @Override
//    public void showWait() {
//        progressContainer.setVisibility(View.VISIBLE);
//        mTabLayout.setVisibility(View.INVISIBLE);
//        mViewPager.setVisibility(View.INVISIBLE);
//        errorContainer.setVisibility(View.INVISIBLE);
//    }
//    @Override
//    public void removeWait() {
//        mViewPager.setVisibility(View.VISIBLE);
//        mTabLayout.setVisibility(View.VISIBLE);
//        progressContainer.setVisibility(View.INVISIBLE);
//    }
//    public void onFailure(String appErrorMessage) {
//        errorContainer.setVisibility(View.VISIBLE);
//        mTabLayout.setVisibility(View.INVISIBLE);
//        mViewPager.setVisibility(View.INVISIBLE);
//        progressContainer.setVisibility(View.INVISIBLE);
//        Timber.e("onFailure %s ", appErrorMessage);
//        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
//    }


    public void setWeatherView() {
//        mSidebarAdapter.addSidebarListItem(weatherData.getLocationName());
        showFragments(createFragments());
    }


    private List<Fragment> createFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new WeatherCurrentFragment(0));
        fragments.add(new WeatherCurrentFragment(1));
        fragments.add(new WeatherForecastFragment());
        Timber.d("createFragments new List<Fragment>.size() is %s", fragments.size());
        return fragments;
    }

    private void showFragments(List<Fragment> fragments) {
        mDaysPagerAdapter = new DaysPagerAdapter(getSupportFragmentManager(), fragments, this);
        mViewPager.setAdapter(mDaysPagerAdapter);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_sun_cloudy);
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_cloud_details);
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_chart);
        mViewPager.setVisibility(View.VISIBLE);
        errorContainer.setVisibility(View.INVISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_location).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSidebarAdapter.addSidebarListItem(query);
                dataViewModel.getWeather(query, setAmountOfDays(), "pl");
                searchView.clearFocus();
                mActionButton.show();
                getSupportActionBar().hide();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mActionButton.setOnClickListener(v -> {
            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibe.vibrate(50);
            searchView.setVisibility(View.VISIBLE);
            MenuItemCompat.expandActionView(menu.findItem(R.id.search_location));
            mActionButton.hide();
            getSupportActionBar().show();
        });
        mAddCity.setOnClickListener(v -> {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
            searchView.setVisibility(View.VISIBLE);
            MenuItemCompat.expandActionView(menu.findItem(R.id.search_location));
            mActionButton.hide();
            getSupportActionBar().show();
        });
        return true;
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState.putSerializable(Constants.WEATHER_DATA, mWeatherData);
//        outState.putStringArrayList(Constants.LIST_OF_CITIES, (ArrayList<String>) mSidebarAdapter.getSidebarListItems());
//        super.onSaveInstanceState(outState);
//    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        if (savedInstanceState != null) {
//            mWeatherData = (WeatherData) savedInstanceState.getSerializable(Constants.WEATHER_DATA);
//            if (mWeatherData != null) {
//                mSearchLocation = mWeatherData.getLocationCurrent();
//                Timber.d(TAG, "onRestoreInstanceState" + " " + "restored weatherData" + mWeatherData.toString());
//                isGeoPossibleFlag = false;
//                mSidebarAdapter.setSidebarListItems(savedInstanceState.getStringArrayList(Constants.LIST_OF_CITIES));
//            }
//        }
//        super.onRestoreInstanceState(savedInstanceState);
//    }

    @Override
    public void onBackPressed() {

        Timber.d("onBackPressed Activity Back Pressed");
        // check for double back press to exit
        if (searchView.isFocused())
            mActionButton.show();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (isBackButtonPressed) {
            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        } else {
            isBackButtonPressed = true;
            Toast.makeText(this, R.string.msg_exit, Toast.LENGTH_SHORT).show();
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    isBackButtonPressed = false;
                }
            }, Constants.DOUBLE_BACK_PRESSED_DELAY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setWeatherView();
//        if (mWeatherData != null) {
//            setWeatherView(mWeatherData);
//            Timber.d(TAG, "onResume Activity" + " " + "creating view from saved instance?");
//        } else if (!isOnline()) {
//            setWeatherView(mDataDao.getWeatherDataByCity(mSearchLocation).get(0));
//            Timber.d(TAG, "onResume Activity" + " " + "creating view from database");
//            Toast.makeText(this, R.string.offline_message, Toast.LENGTH_SHORT).show();
//        } else if (!mViewLoaded && geoLocation.size() == 2 && isGeoPossibleFlag) {
//            isGeoPossibleFlag = false;
//            presenter.getWeather("", geoLocation, setAmountOfDays(), setUnits());
//            Timber.d(TAG, "onResume Activity" + " " + "creating view and getting new WeatherDataCurrent with GPS");
//        } else if (!mViewLoaded) {
//            Timber.d(TAG, "onResume Activity" + " " + "creating view and getting new WeatherDataCurrent");
//            presenter.getWeather(mSearchLocation, Collections.emptyList(), setAmountOfDays(), setUnits());
//        }

    }


    public String getLocation() {
        StringBuilder geoLocation = new StringBuilder();
        try {
            if (checkGPSPermission()) {
                LocationListener locationListener = new MyLocationListener();
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    geoLocation.append(location.getLatitude());
                    geoLocation.append(",");
                    geoLocation.append(location.getLongitude());
                }

                if (geoLocation.length() > 0)
                    locationManager.removeUpdates(locationListener);
                return geoLocation.toString();
            }
        } catch (SecurityException e) {
            Timber.e("getLocationCurrent Error: %s", e.getMessage());
        }
        return geoLocation.toString();
    }

    private void showDialogGPS() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Enable GPS");
        builder.setMessage("Please enable GPS");
        builder.setPositiveButton("Enable", (dialog, which) -> startActivity(
                new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)));
        builder.setNegativeButton("Ignore", (dialog, which) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean gpsStatus() {
        LocationManager mLocManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        return mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public boolean checkGPSPermission() {
        if (CheckPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            if (!gpsStatus()) {
                showDialogGPS();
                Timber.d("checkGPSPermission o to GPS settings");
            } else {
                Timber.d("GPS permission Status OK");
                return true;
            }
        } else {
            RequestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_RUNTIME_PERMISSION);
            Timber.e("RequestPermission case no gps permission");
        }
        return false;
    }

    public void RequestPermission(Activity thisActivity, String Permission, int Code) {
        ActivityCompat.requestPermissions(thisActivity, new String[]{Permission}, Code);
    }

    public boolean CheckPermission(Activity context, String Permission) {
        return ContextCompat.checkSelfPermission(context, Permission) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onSidebarListItemClick(String location) {

        if (isOnline())
            dataViewModel.getWeather(location, setAmountOfDays(), "pl");
        else {
            setWeatherView();
            Toast.makeText(this, R.string.offline_message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick(R.id.app_settings)
    public void goToSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @OnClick(R.id.find_my_location)
    public void getWeatherFromGps() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        dataViewModel.getWeather(geoLocation, setAmountOfDays(), "pl");
    }

}
