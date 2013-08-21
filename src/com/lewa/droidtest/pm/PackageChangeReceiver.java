
package com.lewa.droidtest.pm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class PackageChangeReceiver extends BroadcastReceiver {
    private static final String TAG = "PackageChangeReceiver";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String extraString = "";
        for (String key : extras.keySet()) {
            extraString += "#" + key + "#: " + extras.get(key);
        }
        Log.e(TAG, "action : " + intent.getAction() + "bundle :  " + extraString);
    }
}
