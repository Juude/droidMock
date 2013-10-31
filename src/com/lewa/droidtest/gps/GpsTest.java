package com.lewa.droidtest.gps;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.lewa.droidtest.test.Test;
public class GpsTest extends Test{

    private static final String TAG = "GpsTest";

    public GpsTest(Context context, Bundle extras) {
        super(context, extras);
    }

    @Override
    public void test() {
        state();
    }
    
    public void state() {
        android.content.ContentResolver resolver = mContext.getContentResolver();
        boolean state =  android.provider.Settings.Secure.isLocationProviderEnabled(
                resolver, LocationManager.GPS_PROVIDER);
        Log.e(TAG, "gps state = " + state);
    }
    
}
