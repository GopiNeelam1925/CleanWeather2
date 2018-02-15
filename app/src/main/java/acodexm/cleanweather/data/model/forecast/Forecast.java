
package acodexm.cleanweather.data.model.forecast;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class Forecast {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("forecastday")
    @Expose
    private List<Forecastday> forecastday = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public List<Forecastday> getForecastday() {
        return forecastday;
    }

    public void setForecastday(List<Forecastday> forecastday) {
        this.forecastday = forecastday;
    }

}
