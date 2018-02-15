package acodexm.cleanweather.view.viewmodel;


import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import acodexm.cleanweather.repository.WeatherRepository;

public class BaseViewModel extends ViewModel {
    @Inject
    WeatherRepository weatherRepository;
}
