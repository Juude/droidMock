
package com.lewa.droidtest.adb;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.lewa.droidtest.mock.MockUtils;
import com.lewa.droidtest.mock.Mocker;

public class SettingsTest extends Mocker{
    
    private static final String TAG = "SettingsTest";
    private Context mContext;
    public SettingsTest(Context context, Bundle bundles) {
        super(context, bundles);
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
        String method = MockUtils.getString(null, "method", "setAdb");
        try {
            SettingsTest.class.getMethod(method).invoke(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dump() {
        // TODO Auto-generated method stub
        
    }
}
