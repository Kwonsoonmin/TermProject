package com.example.a1k9s9_000.termproject;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by 1k9s9_000 on 2016-11-24.
 */
// 현재 위치를 알기 위한 class
public class CurrentLocation extends AppCompatActivity implements LocationListener{

    Context mContext;

    boolean isNetWorkEnabled = false;
    boolean isGPSEnabled = false;

    boolean isGetLocation = false;

    Location location;

    double lat;
    double longi;

    long min_distance = 2;
    long min_time = 1000;

    protected LocationManager locationManager;

    public CurrentLocation(Context context) {
        this.mContext = context;
        getLocation();
    }
    // 현재 위치 구하기
    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetWorkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isGPSEnabled && !isNetWorkEnabled) {
                Toast.makeText(CurrentLocation.this, "...Wait Please...", Toast.LENGTH_SHORT).show();
            }else {
                this.isGetLocation = true;

                if(isNetWorkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, min_time, min_distance, this);

                    if(locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if(location != null) {
                            lat = location.getLatitude();
                            longi = location.getLongitude();
                        }
                    }
                }

                if(isGPSEnabled) {
                    if(location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, min_time, min_distance, this);

                        if(locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if(location != null) {
                                lat = location.getLatitude();
                                longi = location.getLongitude();
                            }
                        }
                    }
                }
            }
        } catch(SecurityException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    public double getLat() {
        if(location != null) {
            lat = location.getLatitude();
        }

        return lat;
    }

    public double getLongi() {
        if(location != null) {
            longi = location.getLongitude();
        }

        return longi;
    }

    public boolean isGetLocation() {
        return this.isGetLocation;
    }

    public void onLocationChanged(Location location) {

    }

    public void onStatusChanged(String provider, int status, Bundle extras){

    }

    public void onProviderEnabled(String provider) {

    }

    public void onProviderDisabled(String provider) {

    }
}
