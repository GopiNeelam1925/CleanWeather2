package acodexm.cleanweather.injection;

import acodexm.cleanweather.view.fragments.HomeFragment;
import acodexm.cleanweather.view.fragments.HourlyGraphFragment;
import acodexm.cleanweather.view.fragments.WeatherForecastFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract HomeFragment contributeHomeFragment();

    @ContributesAndroidInjector
    abstract WeatherForecastFragment contributeWeatherForecastFragment();

    @ContributesAndroidInjector
    abstract HourlyGraphFragment contributeHourlyGraphFragment();

}
