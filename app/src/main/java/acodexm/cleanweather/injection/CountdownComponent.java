package acodexm.cleanweather.injection;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import za.co.riggaroo.datecountdown.CountdownApplication;


@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
        CountdownModule.class,
        ActivityBuildersModule.class
        })
public interface CountdownComponent {

    void inject(CountdownApplication countdownApplication);


    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(CountdownApplication application);

        CountdownComponent build();

    }
}