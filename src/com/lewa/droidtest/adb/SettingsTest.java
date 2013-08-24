
package com.lewa.droidtest.adb;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;

import com.lewa.droidtest.test.TestReceiver;

public class SettingsTest {
    
    private Context mContext;
    private Bundle mBundle;
    public SettingsTest(Context context, Bundle bundles) {
        mContext =  context;
        mBundle = bundles;
        String method = TestReceiver.getString(null, "access", "get");
        
        if(method.equals("get")) {
            
        }
        else if(method.equals("set")) {
            
        }
    }
    
    public void getAdb() {
        int adb = Settings.Secure.getInt(mContext.getContentResolver(),
                Settings.Secure.ADB_ENABLED, 0);
    }


    private void setAdb(int value) {
        int adb = Settings.Secure.getInt(mContext.getContentResolver(),
                Settings.Secure.ADB_ENABLED, 0);
        adb = adb == 0 ? 1 : 0;
        Settings.Secure.putInt(mContext.getContentResolver(), Settings.Secure.ADB_ENABLED, adb);
    }
}
