package acodexm.cleanweather.gps;


import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;


public class MyLocationListener implements LocationListener {
    private Location mLocation;

    public MyLocationListener() {
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
    }

    @Override
    public void onStatusChanged(String provider,
                                int status, Bundle extras) {

    }

}