package acodexm.cleanweather.injection;

import acodexm.cleanweather.view.fragments.WeatherCurrentFragment;
import acodexm.cleanweather.view.fragments.WeatherForecastFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract WeatherCurrentFragment contributeHomeFragment();

    @ContributesAndroidInjector
    abstract WeatherForecastFragment contributeWeatherForecastFragment();


}
