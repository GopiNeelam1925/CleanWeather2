
package acodexm.cleanweather.data.model.forecast;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class Forecast {

    @SerializedName("forecastday")
    @Expose
    private List<ForecastDay> forecastDay = null;


    public List<ForecastDay> getForecastDay() {
        return forecastDay;
    }

    public void setForecastDay(List<ForecastDay> forecastDay) {
        this.forecastDay = forecastDay;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "forecastDay=" + forecastDay +
                '}';
    }
}
