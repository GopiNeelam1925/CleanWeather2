
package acodexm.cleanweather.data.model.current;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class LocationCurrent {

    @SerializedName("name")
    @Expose
    private String nameCurrent;
    @SerializedName("region")
    @Expose
    private String regionCurrent;
    @SerializedName("country")
    @Expose
    private String countryCurrent;
    @SerializedName("lat")
    @Expose
    private Double latCurrent;
    @SerializedName("lon")
    @Expose
    private Double lonCurrent;
    @SerializedName("tz_id")
    @Expose
    private String tzIdCurrent;
    @SerializedName("localtime_epoch")
    @Expose
    private Integer localtimeEpochCurrent;
    @SerializedName("localtime")
    @Expose
    private String localtimeCurrent;


    public String getNameCurrent() {
        return nameCurrent;
    }

    public void setNameCurrent(String nameCurrent) {
        this.nameCurrent = nameCurrent;
    }

    public String getRegionCurrent() {
        return regionCurrent;
    }

    public void setRegionCurrent(String regionCurrent) {
        this.regionCurrent = regionCurrent;
    }

    public String getCountryCurrent() {
        return countryCurrent;
    }

    public void setCountryCurrent(String countryCurrent) {
        this.countryCurrent = countryCurrent;
    }

    public Double getLatCurrent() {
        return latCurrent;
    }

    public void setLatCurrent(Double latCurrent) {
        this.latCurrent = latCurrent;
    }

    public Double getLonCurrent() {
        return lonCurrent;
    }

    public void setLonCurrent(Double lonCurrent) {
        this.lonCurrent = lonCurrent;
    }

    public String getTzIdCurrent() {
        return tzIdCurrent;
    }

    public void setTzIdCurrent(String tzIdCurrent) {
        this.tzIdCurrent = tzIdCurrent;
    }

    public Integer getLocaltimeEpochCurrent() {
        return localtimeEpochCurrent;
    }

    public void setLocaltimeEpochCurrent(Integer localtimeEpochCurrent) {
        this.localtimeEpochCurrent = localtimeEpochCurrent;
    }

    public String getLocaltimeCurrent() {
        return localtimeCurrent;
    }

    public void setLocaltimeCurrent(String localtimeCurrent) {
        this.localtimeCurrent = localtimeCurrent;
    }

    @Override
    public String toString() {
        return "LocationCurrent{" +
                "name='" + nameCurrent + '\'' +
                ", regionCurrent='" + regionCurrent + '\'' +
                ", countryCurrent='" + countryCurrent + '\'' +
                ", latCurrent=" + latCurrent +
                ", lonCurrent=" + lonCurrent +
                ", tzIdCurrent='" + tzIdCurrent + '\'' +
                ", localtimeEpochCurrent=" + localtimeEpochCurrent +
                ", localtimeCurrent='" + localtimeCurrent + '\'' +
                '}';
    }
}
