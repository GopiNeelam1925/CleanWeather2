package acodexm.cleanweather.view.fragments;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import acodexm.cleanweather.R;
import acodexm.cleanweather.data.model.WeatherData;
import acodexm.cleanweather.data.model.forecast.ForecastDay;
import acodexm.cleanweather.util.WeatherUtils;
import butterknife.BindView;
import butterknife.ButterKnife;


public class WeatherDataAdapter extends RecyclerView.Adapter<WeatherDataAdapter.WeatherDataViewHolder> {
    private final Context context;
    private List<ForecastDay> items;

    WeatherDataAdapter(Context context) {
        this.context = context;
    }

    @Override
    public WeatherDataViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_weather, parent, false);
        return new WeatherDataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(WeatherDataViewHolder holder, int position) {
        ForecastDay forecastDay = items.get(position);
        holder.mTextDate.setText(forecastDay.getDate());
        holder.mTextDesc.setText(forecastDay.getDay().getCondition().getText());
        holder.mTextTemp.setText(forecastDay.getDay().getAvgtempC().toString());
        holder.mWeatherIcon.setImageResource(WeatherUtils.convertCodeToResource(forecastDay.getDay().getCondition().getCode()).getIcon().getResource());
        holder.itemView.setTag(forecastDay);
    }

    @Override
    public int getItemCount() {
        if (this.items == null)
            this.items = new ArrayList<>();
        return items.size();
    }

    void setItems(WeatherData weatherData) {
        this.items = weatherData.getWeatherDataForecast().getForecast().getForecastDay();
        notifyDataSetChanged();

    }

    class WeatherDataViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.weather_list_date)
        TextView mTextDate;
        @BindView(R.id.weather_list_desc)
        TextView mTextDesc;
        @BindView(R.id.weather_list_temp)
        TextView mTextTemp;
        @BindView(R.id.weather_list_icon)
        AppCompatImageView mWeatherIcon;

        WeatherDataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}