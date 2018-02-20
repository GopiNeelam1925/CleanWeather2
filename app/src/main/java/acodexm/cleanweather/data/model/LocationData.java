package acodexm.cleanweather.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.threeten.bp.LocalDateTime;

import static acodexm.cleanweather.data.model.LocationData.TABLE_NAME;


@Entity(tableName = TABLE_NAME)
public class LocationData {
    public static final String TABLE_NAME = "location_data";
    public static final String LOCATION_FIELD = "city_location";
    public static final String DATE_FIELD = "insert_timestamp";
    @PrimaryKey
    @ColumnInfo(name = LOCATION_FIELD)
    @NonNull
    private String location = "";
    @ColumnInfo(name = DATE_FIELD)
    private LocalDateTime timestamp;


    public LocationData() {
    }

    public LocationData(LocationData locationData) {
        this.location = locationData.location;
        this.timestamp = LocalDateTime.now();
    }

    @Ignore
    public LocationData(@NonNull String location, LocalDateTime timestamp) {
        this.location = location;
        this.timestamp = timestamp;
    }

    @NonNull
    public String getLocation() {
        return location;
    }

    public void setLocation(@NonNull String location) {
        this.location = location;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public String toString() {
        return "location{" +
                "location='" + location + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof LocationData) && ((LocationData) obj).getLocation().equals(this.getLocation());
    }
}
