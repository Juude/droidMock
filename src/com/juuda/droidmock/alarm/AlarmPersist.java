package com.juuda.droidmock.alarm;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.juuda.droidmock.mock.Mocker;
public class AlarmPersist extends Mocker{
    public static final String TAG = "AlarmPersist";
    
    
    public AlarmPersist(Context context, Bundle bundle) {
        super(context, bundle);
    }
    public void getAlarmPersist() {
        Log.e(TAG, "alarm persist" + 
              lewa.content.AlarmPersists.getInstance(mContext)
              .getPackages()
              );
    }
    @Override
    public void dump() {
        getAlarmPersist();
    }
}
