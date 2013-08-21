
package com.lewa.droidtest.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;


public class AlarmManagerTest {
    private static final String TAG = "AlarmManagerTest";
    private BroadcastReceiver mReceiver;

    public void setupCloseTaskIfNeeded(Context context) {
        long currentMills = System.currentTimeMillis();
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        //PendingIntent operation = PendingIntent.getActivity(context, 0, new Intent("com.lewa.action.AUTO_STARTS_ACTIVITY"),
        //        PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent operation = PendingIntent.getBroadcast(context, 0, new Intent(TAG),
                PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP , currentMills + 2000, operation);
    }
    
    public static void cancelCloseTask(Context context) {
        //PendingIntent operation = 
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        //PendingIntent operation = PendingIntent.getActivity(context, 0, new Intent("com.lewa.action.AUTO_STARTS_ACTIVITY"),
        //        PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent operation = PendingIntent.getBroadcast(context, 0, new Intent(TAG),
                PendingIntent.FLAG_UPDATE_CURRENT);
        operation.cancel();//works
        //am.cancel(operation);//works
    }
    
    public void startTest(Context context, Bundle bundle) {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e(TAG, "received.. TAG");
            }
        };
        context.getApplicationContext().registerReceiver(mReceiver, new IntentFilter(TAG));
        setupCloseTaskIfNeeded(context);
        if(bundle.getString("close") != null) {
            Log.e(TAG, "please close");
            cancelCloseTask(context);
        }
    }
    
    public void endTest(Context context) {
        context.getApplicationContext().unregisterReceiver(mReceiver);
    }
}