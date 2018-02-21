package acodexm.cleanweather.view.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import acodexm.cleanweather.R;
import acodexm.cleanweather.injection.Injectable;
import acodexm.cleanweather.injection.ViewModelFactory;
import acodexm.cleanweather.view.viewmodel.LocationDataViewModel;
import acodexm.cleanweather.view.viewmodel.WeatherDataViewModel;
import timber.log.Timber;

public class WeatherForecastFragment extends Fragment implements Injectable {
    @Inject
    ViewModelFactory viewModelFactory;
    private WeatherDataAdapter adapter;
    private WeatherDataViewModel weatherViewModel;
    private LocationDataViewModel locationViewModel;

    public WeatherForecastFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_forecast, container, false);
        setupRecyclerView(view);
        weatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherDataViewModel.class);
        locationViewModel = ViewModelProviders.of(this, viewModelFactory).get(LocationDataViewModel.class);
        Timber.d("onCreateView");
        setData();
        return view;
    }

    private void setData() {
        try {
            locationViewModel.getCurrentLocation().observe(this,
                    locationData -> {
                        if (locationData != null) {
                            weatherViewModel.getWeatherData(locationData.getLocation()).observe(this,
                                    data -> {
                                        if (data != null) {
                                            Timber.d("WeatherData Changed:%s", data);
                                            adapter.setItems(data);
                                        }
                                    });
                        }
                    });
        } catch (Exception e) {
            Timber.d(e, "Failed to load location");
        }

    }


    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_list_forecast);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new WeatherDataAdapter(getContext());
        recyclerView.setAdapter(adapter);
        final DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

}
