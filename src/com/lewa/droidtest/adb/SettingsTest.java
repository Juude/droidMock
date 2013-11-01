
package com.lewa.droidtest.adb;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.lewa.droidtest.test.TestUtils;

public class SettingsTest {
    
    private static final String TAG = "SettingsTest";
    private Context mContext;
    public SettingsTest(Context context, Bundle bundles) {
        mContext =  context;
    }
    
    public void getAdb() {
        int adb = Settings.Secure.getInt(mContext.getContentResolver(),
                Settings.Secure.ADB_ENABLED, 0);
        Log.e(TAG, "getAdb" + adb);
    }


    public void setAdb() {
        int adb = Settings.Secure.getInt(mContext.getContentResolver(),
                Settings.Secure.ADB_ENABLED, 0);
        adb = adb == 0 ? 1 : 0;
        Settings.Secure.putInt(mContext.getContentResolver(), Settings.Secure.ADB_ENABLED, adb);
        getAdb();
    }

    public void test() {
        String method = TestUtils.getString(null, "method", "setAdb");
        try {
            SettingsTest.class.getMethod(method).invoke(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
