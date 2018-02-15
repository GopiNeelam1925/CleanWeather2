package acodexm.cleanweather.view.fragments;

import android.arch.lifecycle.ViewModelProvider;
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
import acodexm.cleanweather.data.model.WeatherData;
import acodexm.cleanweather.injection.Injectable;
import acodexm.cleanweather.view.viewmodel.WeatherDataViewModel;
import timber.log.Timber;

public class WeatherForecastFragment extends Fragment implements Injectable {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private WeatherDataAdapter adapter;
    private WeatherDataViewModel weatherDataViewModel;

//    private View.OnClickListener itemClickListener = v -> {
//        WeatherData data = (WeatherData) v.getTag();
//
//        Toast.makeText(getContext(), "Clicked:" + data.getName(), Toast.LENGTH_LONG).show();
//    };

    public static Fragment newInstance(WeatherData weatherData) {
        Timber.d("newInstance: " + weatherData.toString());
        return new WeatherForecastFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_forecast, container, false);
        setupRecyclerView(view);
        weatherDataViewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherDataViewModel.class);
        weatherDataViewModel.getWeatherData().observe(this, data -> {
            Timber.d("WeatherData Changed:%s", data);
            adapter.setItems(data);
        });
        return view;
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
