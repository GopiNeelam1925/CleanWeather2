
package acodexm.cleanweather.data.model.current;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class ConditionCurrent {

    @SerializedName("text")
    @Expose
    private String textCurrent;
    @SerializedName("icon")
    @Expose
    private String iconCurrent;
    @SerializedName("code")
    @Expose
    private Integer codeCurrent;


    public String getTextCurrent() {
        return textCurrent;
    }

    public void setTextCurrent(String textCurrent) {
        this.textCurrent = textCurrent;
    }

    public String getIconCurrent() {
        return iconCurrent;
    }

    public void setIconCurrent(String iconCurrent) {
        this.iconCurrent = iconCurrent;
    }

    public Integer getCodeCurrent() {
        return codeCurrent;
    }

    public void setCodeCurrent(Integer codeCurrent) {
        this.codeCurrent = codeCurrent;
    }

    @Override
    public String toString() {
        return "ConditionCurrent{" +
                "textCurrent='" + textCurrent + '\'' +
                ", iconCurrent='" + iconCurrent + '\'' +
                ", codeCurrent=" + codeCurrent +
                '}';
    }
}
