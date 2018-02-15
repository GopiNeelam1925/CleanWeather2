package acodexm.cleanweather.injection;

import acodexm.cleanweather.view.activities.HomeActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract HomeActivity bindHomeActivity();

}
