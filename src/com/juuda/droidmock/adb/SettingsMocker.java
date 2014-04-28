
package com.juuda.droidmock.adb;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.juuda.droidmock.mock.MockUtils;
import com.juuda.droidmock.mock.Mocker;
import com.juuda.droidmock.su.SuExecuter;
import com.juuda.droidmock.su.SuExecuter.CommandResult;


public class SettingsMocker extends Mocker{
    private static final String TAG = "SettingsMocker";
    public SettingsMocker(Context context, Bundle bundles) {
        super(context, bundles);
    }
   
    public void toggleAdb() {
        int adb = Settings.Secure.getInt(mContext.getContentResolver(),
                Settings.Global.ADB_ENABLED, 0);
        adb = adb == 0 ? 1 : 0;
        Log.d(TAG, "set adb to" + adb);
        CommandResult result = SuExecuter.runCommandForResult("settings put global adb_enabled " + adb, true);
        if(!result.success) {
            Log.e(TAG, "result : " + result.result);
        }
    }
    
    @Override
    public void dump() {
        String method = MockUtils.getString(mExtras, "method", "toggleAdb");
        try {
            SettingsMocker.class.getMethod(method).invoke(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
