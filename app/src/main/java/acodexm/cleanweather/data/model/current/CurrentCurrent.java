
package acodexm.cleanweather.data.model.current;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class CurrentCurrent {

    @SerializedName("last_updated_epoch")
    @Expose
    private Integer lastUpdatedEpochCurrent;
    @SerializedName("last_updated")
    @Expose
    private String lastUpdatedCurrent;
    @SerializedName("temp_c")
    @Expose
    private Double tempCCurrent;
    @SerializedName("temp_f")
    @Expose
    private Double tempFCurrent;
    @SerializedName("is_day")
    @Expose
    private Integer isDayCurrent;
    @SerializedName("conditionCurrentCurrent")
    @Expose
    @Embedded
    private ConditionCurrent conditionCurrentCurrent;
    @SerializedName("wind_mph")
    @Expose
    private Double windMphCurrent;
    @SerializedName("wind_kph")
    @Expose
    private Double windKphCurrent;
    @SerializedName("wind_degree")
    @Expose
    private Integer windDegreeCurrent;
    @SerializedName("wind_dir")
    @Expose
    private String windDirCurrent;
    @SerializedName("pressure_mb")
    @Expose
    private Double pressureMbCurrent;
    @SerializedName("pressure_in")
    @Expose
    private Double pressureInCurrent;
    @SerializedName("precip_mm")
    @Expose
    private Double precipMmCurrent;
    @SerializedName("precip_in")
    @Expose
    private Double precipInCurrent;
    @SerializedName("humidityCurrent")
    @Expose
    private Integer humidityCurrent;
    @SerializedName("cloudCurrent")
    @Expose
    private Integer cloudCurrent;
    @SerializedName("feelslike_c")
    @Expose
    private Double feelslikeCCurrent;
    @SerializedName("feelslike_f")
    @Expose
    private Double feelslikeFCurrent;
    @SerializedName("vis_km")
    @Expose
    private Double visKmCurrent;
    @SerializedName("vis_miles")
    @Expose
    private Double visMilesCurrent;

    public Integer getLastUpdatedEpochCurrent() {
        return lastUpdatedEpochCurrent;
    }

    public void setLastUpdatedEpochCurrent(Integer lastUpdatedEpochCurrent) {
        this.lastUpdatedEpochCurrent = lastUpdatedEpochCurrent;
    }

    public String getLastUpdatedCurrent() {
        return lastUpdatedCurrent;
    }

    public void setLastUpdatedCurrent(String lastUpdatedCurrent) {
        this.lastUpdatedCurrent = lastUpdatedCurrent;
    }

    public Double getTempCCurrent() {
        return tempCCurrent;
    }

    public void setTempCCurrent(Double tempCCurrent) {
        this.tempCCurrent = tempCCurrent;
    }

    public Double getTempFCurrent() {
        return tempFCurrent;
    }

    public void setTempFCurrent(Double tempFCurrent) {
        this.tempFCurrent = tempFCurrent;
    }

    public Integer getIsDayCurrent() {
        return isDayCurrent;
    }

    public void setIsDayCurrent(Integer isDayCurrent) {
        this.isDayCurrent = isDayCurrent;
    }

    public ConditionCurrent getConditionCurrentCurrent() {
        return conditionCurrentCurrent;
    }

    public void setConditionCurrentCurrent(ConditionCurrent conditionCurrentCurrent) {
        this.conditionCurrentCurrent = conditionCurrentCurrent;
    }

    public Double getWindMphCurrent() {
        return windMphCurrent;
    }

    public void setWindMphCurrent(Double windMphCurrent) {
        this.windMphCurrent = windMphCurrent;
    }

    public Double getWindKphCurrent() {
        return windKphCurrent;
    }

    public void setWindKphCurrent(Double windKphCurrent) {
        this.windKphCurrent = windKphCurrent;
    }

    public Integer getWindDegreeCurrent() {
        return windDegreeCurrent;
    }

    public void setWindDegreeCurrent(Integer windDegreeCurrent) {
        this.windDegreeCurrent = windDegreeCurrent;
    }

    public String getWindDirCurrent() {
        return windDirCurrent;
    }

    public void setWindDirCurrent(String windDirCurrent) {
        this.windDirCurrent = windDirCurrent;
    }

    public Double getPressureMbCurrent() {
        return pressureMbCurrent;
    }

    public void setPressureMbCurrent(Double pressureMbCurrent) {
        this.pressureMbCurrent = pressureMbCurrent;
    }

    public Double getPressureInCurrent() {
        return pressureInCurrent;
    }

    public void setPressureInCurrent(Double pressureInCurrent) {
        this.pressureInCurrent = pressureInCurrent;
    }

    public Double getPrecipMmCurrent() {
        return precipMmCurrent;
    }

    public void setPrecipMmCurrent(Double precipMmCurrent) {
        this.precipMmCurrent = precipMmCurrent;
    }

    public Double getPrecipInCurrent() {
        return precipInCurrent;
    }

    public void setPrecipInCurrent(Double precipInCurrent) {
        this.precipInCurrent = precipInCurrent;
    }

    public Integer getHumidityCurrent() {
        return humidityCurrent;
    }

    public void setHumidityCurrent(Integer humidityCurrent) {
        this.humidityCurrent = humidityCurrent;
    }

    public Integer getCloudCurrent() {
        return cloudCurrent;
    }

    public void setCloudCurrent(Integer cloudCurrent) {
        this.cloudCurrent = cloudCurrent;
    }

    public Double getFeelslikeCCurrent() {
        return feelslikeCCurrent;
    }

    public void setFeelslikeCCurrent(Double feelslikeCCurrent) {
        this.feelslikeCCurrent = feelslikeCCurrent;
    }

    public Double getFeelslikeFCurrent() {
        return feelslikeFCurrent;
    }

    public void setFeelslikeFCurrent(Double feelslikeFCurrent) {
        this.feelslikeFCurrent = feelslikeFCurrent;
    }

    public Double getVisKmCurrent() {
        return visKmCurrent;
    }

    public void setVisKmCurrent(Double visKmCurrent) {
        this.visKmCurrent = visKmCurrent;
    }

    public Double getVisMilesCurrent() {
        return visMilesCurrent;
    }

    public void setVisMilesCurrent(Double visMilesCurrent) {
        this.visMilesCurrent = visMilesCurrent;
    }

    @Override
    public String toString() {
        return "CurrentCurrent{" +
                "lastUpdatedEpochCurrent=" + lastUpdatedEpochCurrent +
                ", lastUpdatedCurrent='" + lastUpdatedCurrent + '\'' +
                ", tempCCurrent=" + tempCCurrent +
                ", tempFCurrent=" + tempFCurrent +
                ", isDayCurrent=" + isDayCurrent +
                ", conditionCurrentCurrent=" + conditionCurrentCurrent +
                ", windMphCurrent=" + windMphCurrent +
                ", windKphCurrent=" + windKphCurrent +
                ", windDegreeCurrent=" + windDegreeCurrent +
                ", windDirCurrent='" + windDirCurrent + '\'' +
                ", pressureMbCurrent=" + pressureMbCurrent +
                ", pressureInCurrent=" + pressureInCurrent +
                ", precipMmCurrent=" + precipMmCurrent +
                ", precipInCurrent=" + precipInCurrent +
                ", humidityCurrent=" + humidityCurrent +
                ", cloudCurrent=" + cloudCurrent +
                ", feelslikeCCurrent=" + feelslikeCCurrent +
                ", feelslikeFCurrent=" + feelslikeFCurrent +
                ", visKmCurrent=" + visKmCurrent +
                ", visMilesCurrent=" + visMilesCurrent +
                '}';
    }
}
