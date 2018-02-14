package acodexm.cleanweather.injection;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import za.co.riggaroo.datecountdown.ui.event.add.AddEventActivity;
import za.co.riggaroo.datecountdown.ui.event.list.EventListActivity;


@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract AddEventActivity bindAddEventActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract EventListActivity bindEventListActivity();

}
