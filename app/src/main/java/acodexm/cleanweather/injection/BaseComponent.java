package acodexm.cleanweather.injection;

import javax.inject.Singleton;

import acodexm.cleanweather.BaseApp;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        BaseModule.class,
        ActivityBuildersModule.class
})
public interface BaseComponent {
    void inject(BaseApp baseApp);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(BaseApp app);
        BaseComponent build();
    }
}