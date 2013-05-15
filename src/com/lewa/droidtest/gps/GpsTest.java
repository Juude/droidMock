package com.lewa.droidtest.gps;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;

public class GpsTest {
    
    
    private static final String TAG = "GpsTest";

    public void noName(Context context)
    {
        Settings.Secure.setLocationProviderEnabled(context.getContentResolver(), LocationManager.GPS_PROVIDER, true);

        LocationManager locMan = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        
        Location loc = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        
        if (loc != null) {
        
           double lat = loc.getLatitude();
        
           Log.d(TAG, "latitude: " + lat);
        
           double lng = loc.getLongitude();
        
           Log.d(TAG, "longitude: " + lng);
        
        }
    
    }
}
