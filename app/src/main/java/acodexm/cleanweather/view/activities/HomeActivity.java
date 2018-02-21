package acodexm.cleanweather.view.activities;


import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import acodexm.cleanweather.R;
import acodexm.cleanweather.data.model.LocationData;
import acodexm.cleanweather.data.model.WeatherData;
import acodexm.cleanweather.gps.MyLocationListener;
import acodexm.cleanweather.injection.ViewModelFactory;
import acodexm.cleanweather.util.Constants;
import acodexm.cleanweather.view.custom.MyViewPager;
import acodexm.cleanweather.view.custom.ZoomOutPageTransformer;
import acodexm.cleanweather.view.fragments.DaysPagerAdapter;
import acodexm.cleanweather.view.fragments.SidebarAdapter;
import acodexm.cleanweather.view.fragments.WeatherCurrentFragment;
import acodexm.cleanweather.view.fragments.WeatherForecastFragment;
import acodexm.cleanweather.view.viewmodel.LocationDataViewModel;
import acodexm.cleanweather.view.viewmodel.ModelViewControl;
import acodexm.cleanweather.view.viewmodel.WeatherDataViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        SidebarAdapter.SidebarUserClickAction, HasSupportFragmentInjector, ModelViewControl {
    private static final long EXPIRE_TIME = 10 * 60 * 1000;
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
    private String location;
    private String language;
    private boolean isBackButtonPressed;
    private SharedPreferences mSharedPreferences;
    private SidebarAdapter mSidebarAdapter;
    private DaysPagerAdapter mDaysPagerAdapter;
    private WeatherDataViewModel weatherViewModel;
    private LocationDataViewModel locationViewModel;
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
        weatherViewModel = ViewModelProviders.of(this, modelFactory).get(WeatherDataViewModel.class);
        locationViewModel = ViewModelProviders.of(this, modelFactory).get(LocationDataViewModel.class);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        try {
            locationViewModel.getCurrentLocation().observe(this,
                    locationData -> {
                        if (locationData != null) {
                            weatherViewModel.getWeatherData(locationData.getLocation()).observe(this,
                                    data -> {
                                        if (data != null) {
                                            location = data.getLocationName();
                                        }
                                    });
                        }
                    });
        } catch (Exception e) {
            Timber.d(e, "getLocation null exception");
        }
        if (checkGPSPermission() && location == null) {
            location = getGPSLocation();
        }
        language = Locale.getDefault().getLanguage();
        Timber.d("onCreate");
        fetchWeather(location);
        mViewPager.setHomeActivity(this);
        mSidebarAdapter = new SidebarAdapter();
        mSidebarAdapter.setSidebarClickListener(this, this);
        mSidebarList.setLayoutManager(new LinearLayoutManager(this));
        mSidebarList.setAdapter(mSidebarAdapter);
        setWeatherView();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }

    public int getCurrentPage() {
        return mViewPager.getCurrentItem();

    }

    private void fetchWeather(String location) {
        Timber.d("current location fetch request %s", location);
        if (isOnline()) {
            weatherViewModel.fetchWeather(location, setAmountOfDays(), language).observe(this,
                    response -> {
                        switch (response.status) {
                            case ERROR:
                                Timber.d("ERROR");
                                onFailure(response.error.getAppErrorMessage());
                                break;
                            case LOADING:
                                Timber.d("LOADING");
                                showWait();
                                break;
                            case SUCCESS:
                                Timber.d("SUCCESS");
                                weatherViewModel.addWeatherData(response.data);
                                removeWait();
                                break;
                        }
                    });
        } else showToast(Constants.NETWORK_ERROR_MESSAGE);

    }

    public DaysPagerAdapter getDaysPagerAdapter() {
        return mDaysPagerAdapter;
    }

    public void updateWeather() {
        Timber.d("updateWeather: ");
        locationViewModel.getCurrentLocation().observe(this, locationData -> {
            if (locationData != null) fetchWeather(locationData.getLocation());
        });
    }

    public int setAmountOfDays() {
        return Integer.valueOf(mSharedPreferences.getString(Constants.SETTING_DAY_LIST, 7 + ""));
    }


    public void showWait() {
        Timber.d("showWait");
        progressContainer.setVisibility(View.VISIBLE);
        mTabLayout.setVisibility(View.INVISIBLE);
        mViewPager.setVisibility(View.INVISIBLE);
        errorContainer.setVisibility(View.INVISIBLE);
    }


    public void removeWait() {
        Timber.d("removeWait");
        mViewPager.setVisibility(View.VISIBLE);
        mTabLayout.setVisibility(View.VISIBLE);
        progressContainer.setVisibility(View.INVISIBLE);
    }

    public void onSuccess() {
        Timber.d("onSuccess");
        errorContainer.setVisibility(View.INVISIBLE);
    }

    public void onFailure(String appErrorMessage) {
        errorContainer.setVisibility(View.VISIBLE);
        mTabLayout.setVisibility(View.INVISIBLE);
        mViewPager.setVisibility(View.INVISIBLE);
        progressContainer.setVisibility(View.INVISIBLE);
        Timber.e("onFailure %s ", appErrorMessage);
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
    }

    public void setWeatherView() {
        showFragments(createFragments());
    }


    private List<Fragment> createFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(WeatherCurrentFragment.newInstance(0));
        fragments.add(WeatherCurrentFragment.newInstance(1));
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
                LocationData locationData = new LocationData(query, LocalDateTime.now());
                mSidebarAdapter.addSidebarListItem(locationData);
                fetchWeather(query);
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
            showToast(R.string.msg_exit);
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
        Timber.d("onResume");
        locationViewModel.getLocationList().observe(this,
                dataList -> mSidebarAdapter.setSidebarListItems(dataList));

//        if (mWeatherData != null) {
//            setWeatherView(mWeatherData);
//            Timber.d(TAG, "onResume Activity" + " " + "creating view from saved instance?");
//        } else if (!isOnline()) {
//            setWeatherView(mDataDao.getWeatherDataByCity(mSearchLocation).get(0));
//            Timber.d(TAG, "onResume Activity" + " " + "creating view from database");
//            Toast.makeText(this, R.string.offline_message, Toast.LENGTH_SHORT).show();
//        } else if (!mViewLoaded && location.size() == 2 && isGeoPossibleFlag) {
//            isGeoPossibleFlag = false;
//            presenter.fetchWeather("", location, setAmountOfDays(), setUnits());
//            Timber.d(TAG, "onResume Activity" + " " + "creating view and getting new WeatherDataCurrent with GPS");
//        } else if (!mViewLoaded) {
//            Timber.d(TAG, "onResume Activity" + " " + "creating view and getting new WeatherDataCurrent");
//            presenter.fetchWeather(mSearchLocation, Collections.emptyList(), setAmountOfDays(), setUnits());
//        }

    }


    public String getGPSLocation() {
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
            } else {
                showToast("GPS disabled");
            }
        } catch (SecurityException e) {
            Timber.e("getLocationCurrent Error: %s", e.getMessage());
        }
        return geoLocation.toString();
    }

    private boolean checkGPSPermission() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onSidebarListItemClick(LocationData location) {
        locationViewModel.addLocation(new LocationData(location));
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


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
        fetchWeather(getGPSLocation());

    }

    @Override
    public void deleteWeather(WeatherData weatherData) {
        weatherViewModel.getWeatherData(weatherData.getLocationName()).observe(this, data -> {
            if (data != null) weatherViewModel.deleteWeatherData(data);
        });
    }

    @Override
    public void deleteLocation(LocationData location) {
        locationViewModel.deleteLocation(location);
        weatherViewModel.getWeatherData(location.getLocation()).observe(this, data -> {
            if (data != null) weatherViewModel.deleteWeatherData(data);
        });
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
