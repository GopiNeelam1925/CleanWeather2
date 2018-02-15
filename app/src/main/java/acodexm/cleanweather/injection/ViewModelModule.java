package acodexm.cleanweather.injection;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import acodexm.cleanweather.view.viewmodel.WeatherDataViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherDataViewModel.class)
    abstract ViewModel bindWeatherDataViewModel(WeatherDataViewModel weatherDataViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

}
