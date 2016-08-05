package com.juuda.droidmock.gps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.juuda.droidmock.mock.MockUtils;
import com.juuda.droidmock.mock.Mocker;

public class GpsTest extends Mocker {

    private static final String TAG = "GpsTest";
    private LocationManager mLocationManager;

    public GpsTest(Context context, Bundle extras) {
        super(context, extras);
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void dump() {
        try {
            GpsTest.class.getMethod(MockUtils.getString(mExtras, "method", "state"))
                    .invoke(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLocation() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.e(TAG, "location : " + location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    private int checkSelfPermission(String accessFineLocation) {
        return 0;

    }

    public void state() {
        android.content.ContentResolver resolver = mContext.getContentResolver();
        boolean gpsOn = android.provider.Settings.Secure.isLocationProviderEnabled(
                resolver, LocationManager.GPS_PROVIDER);

        boolean networkOn = android.provider.Settings.Secure.isLocationProviderEnabled(
                resolver, LocationManager.NETWORK_PROVIDER);

        Log.e(TAG, "[gps on = " + gpsOn + "] [networkOn : " + networkOn + "]");

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        Location lastGps = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location lastNetwork =  mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        
        Log.e(TAG, String.format("[lastGps : %s], [lastNetwork : %s]", lastGps, lastNetwork));

        
    }
    
}
