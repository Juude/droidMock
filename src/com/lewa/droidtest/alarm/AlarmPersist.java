package com.lewa.droidtest.alarm;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
public class AlarmPersist {
    public static final String TAG = "AlarmPersist";
    
    public Context mContext;
    public Bundle mBundle;
    
    public AlarmPersist(Context context, Bundle bundle) {
        mContext = context;
        mBundle = bundle;
    }
    public void getAlarmPersist() {
        
        Log.e(TAG, "alarm persist" + 
              lewa.content.AlarmPersists.getInstance(mContext)
              .getPackages()
              );
    }
}
