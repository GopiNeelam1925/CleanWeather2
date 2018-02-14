
package acodexm.cleanweather.data.model.current;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class WeatherDataCurrent {
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("current")
    @Expose
    private Current current;


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return "WeatherDataCurrent{" +
                "location=" + location +
                ", current=" + current +
                '}';
    }
}
