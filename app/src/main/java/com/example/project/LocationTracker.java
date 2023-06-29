package com.example.project;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class LocationTracker extends Service implements LocationListener,ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int GPS_PERMISSION_REQUEST_CODE = 1001;
    private Context mContext = null;
    boolean canGetLocation = false;    // flag for GPS status

    Location location;
    double latitude;
    double longitude;
    protected LocationManager locationManager;

    public LocationTracker() {
        getLocation();
    }

    public LocationTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    private void getLocation() {
        this.canGetLocation = false;
        int permissionState1 = ActivityCompat.checkSelfPermission(mContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionState2 = ActivityCompat.checkSelfPermission(mContext,
                android.Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permissionState1 == PackageManager.PERMISSION_GRANTED &&
                permissionState2 == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
            this.canGetLocation = true;
        } else {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    GPS_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == GPS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        GPS_PERMISSION_REQUEST_CODE);
            }
        }
    }
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude()  {
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public boolean CanGetLocation() {
        getLocation();
        return canGetLocation;
    }

    //Calling this function will stop using GPS in app
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(LocationTracker.this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        getLocation();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}