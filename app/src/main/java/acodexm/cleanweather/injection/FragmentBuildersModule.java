package acodexm.cleanweather.injection;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import za.co.riggaroo.datecountdown.ui.event.add.AddEventFragment;
import za.co.riggaroo.datecountdown.ui.event.list.EventListFragment;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract EventListFragment contributeEventListFragment();

    @ContributesAndroidInjector
    abstract AddEventFragment contributeAddEventFragment();
}
