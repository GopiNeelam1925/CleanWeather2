package acodexm.cleanweather.injection;

import acodexm.cleanweather.view.list.WeatherDataFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract WeatherDataFragment contributeEventListFragment();

}
