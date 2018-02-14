package acodexm.cleanweather.injection;

import acodexm.cleanweather.view.list.WeatherDataActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract WeatherDataActivity bindWeatherDataActivity();

}
