package acodexm.cleanweather.view.list;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import acodexm.cleanweather.R;
import acodexm.cleanweather.data.model.WeatherData;
import acodexm.cleanweather.data.model.forecast.Forecastday;
import acodexm.cleanweather.util.WeatherUtils;
import butterknife.BindView;
import butterknife.ButterKnife;


public class WeatherDataAdapter extends RecyclerView.Adapter<WeatherDataAdapter.WeatherDataViewHolder> {
    private final Context context;
    private List<Forecastday> items;

    WeatherDataAdapter(WeatherData items, Context context) {
        this.items = items.getWeatherDataForecast().getForecast().getForecastday();
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
        Forecastday forecastday = items.get(position);
        holder.mTextDate.setText(forecastday.getDate());
        holder.mTextDesc.setText(forecastday.getDay().getCondition().getText());
        holder.mTextTemp.setText(forecastday.getDay().getAvgtempC().toString());
        holder.mWeatherIcon.setImageResource(WeatherUtils.convertIconToResource(forecastday.getDay().getCondition().getIcon()));
        holder.itemView.setTag(forecastday);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void setItems(WeatherData weatherDatas) {
        this.items = weatherDatas.getWeatherDataForecast().getForecast().getForecastday();
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