package acodexm.cleanweather.view.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class WeatherDataAdapter extends RecyclerView.Adapter<WeatherDataViewHolder> {
    private final Context context;
    private List<WeatherData> items;
    private View.OnClickListener deleteClickListener;
    private View.OnClickListener viewClickListener;

    WeatherDataAdapter(List<WeatherData> items, Context context, View.OnClickListener viewClickListener, View.OnClickListener deleteClickListener) {
        this.items = items;
        this.context = context;
        this.deleteClickListener = deleteClickListener;
        this.viewClickListener = viewClickListener;
    }

    @Override
    public WeatherDataViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_weatherData, parent, false);
        return new WeatherDataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(WeatherDataViewHolder holder, int position) {
        WeatherData item = items.get(position);
        holder.weatherDataTextView.setText(item.getName());
        holder.descriptionTextView.setText(item.getDescription());
        holder.countdownTextView.setText(context.getString(R.string.days_until, item.getDaysUntil()));
        holder.itemView.setTag(item);
        holder.deleteButton.setTag(item);
        holder.deleteButton.setOnClickListener(deleteClickListener);
        holder.itemView.setOnClickListener(viewClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void setItems(List<WeatherData> weatherDatas) {
        this.items = weatherDatas;
        notifyDataSetChanged();
    }
}