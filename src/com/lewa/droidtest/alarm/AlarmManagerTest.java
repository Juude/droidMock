
package com.lewa.droidtest.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.ServiceManager;
import android.util.Log;

import com.lewa.droidtest.mock.MockUtils;
import com.lewa.droidtest.mock.Mocker;


public class AlarmManagerTest extends Mocker{
    public AlarmManagerTest(Context context, Bundle extras) {
        super(context, extras);
    }

    private static final String TAG = "AlarmManagerTest";
    private BroadcastReceiver mReceiver;
    private long mDelay = 1000;
    
    public void setTask(Context context) {
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        //PendingIntent operation = PendingIntent.getActivity(context, 0, 
        //        new Intent("com.android.deskclock.ALARM_ALERT"),
        //        PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent operation = PendingIntent.getBroadcast(context, 0, 
                new Intent("com.android.deskclock.ALARM_ALERT"),
                PendingIntent.FLAG_UPDATE_CURRENT);
        
        long currentMills = System.currentTimeMillis();
        am.set(AlarmManager.RTC_WAKEUP , currentMills + mDelay, operation);
    }
    
    public static void unsetTask(Context context) {
        //PendingIntent operation = 
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        //PendingIntent operation = PendingIntent.getActivity(context, 0, new Intent("com.lewa.action.AUTO_STARTS_ACTIVITY"),
        //        PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent operation = PendingIntent.getBroadcast(context, 0, new Intent(TAG),
                PendingIntent.FLAG_UPDATE_CURRENT);
        
        operation.cancel();//works
        am.cancel(operation);//works
    }
    
    public static void getAlarms(Context context) {
        ServiceManager.getService(Context.ALARM_SERVICE);
    }
    
    public void test(Context context, Bundle bundle) {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e(TAG, "received.. TAG");
            }
        };
        mDelay = MockUtils.getInt(bundle, "delay", 1000);
        context.getApplicationContext().registerReceiver(mReceiver, new IntentFilter(TAG));
        setTask(context);
        if(bundle.getString("close") != null) {
            Log.e(TAG, "please close");
            unsetTask(context);
        }
    }
    
    public void endTest(Context context) {
        context.getApplicationContext().unregisterReceiver(mReceiver);
    }

    @Override
    public void dump() {
        test(mContext, mExtras);
        endTest(mContext);
    }
}