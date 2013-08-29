package com.lewa.droidtest.test;

import java.lang.reflect.Method;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lewa.droidtest.alarm.AlarmManagerTest;
import com.lewa.droidtest.alarm.AlarmPersist;
import com.lewa.droidtest.data.ParcelableTest;
import com.lewa.droidtest.pm.Pm;
import com.lewa.droidtest.am.Am;
import com.lewa.droidtest.screen.ScreenTest;

import com.lewa.droidtest.test.TestUtils.TestMe;

public class TestReceiver extends BroadcastReceiver{
    private static final String TAG = "TestReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            final String action = getString(intent.getExtras(), "action", "ACTION!!");
            Log.e(TAG, "com.lewa.droidtest executing..." + action);
            Method actionMethod =  getClass().getMethod(action, Context.class, Intent.class);
            if(actionMethod.getAnnotation(TestUtils.TestMe.class) != null) {
                actionMethod.invoke(this, context,intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    public static String getString(Bundle bundle, String key, String def) {
        String result = bundle.getString(key);
        result = result == null ? def : result;
        return result;
    }
    
    public static int getInt(Bundle bundle, String key, int def) {
        String str = bundle.getString(key);
        int result = def;
        try {
            result = Integer.parseInt(str);
        }
        catch(Exception e) {
        }
        return result;
    }
    
    @TestMe
    public void parcelable(Context context, Intent intent) {
        ParcelableTest.test();
    }
    
    @TestMe
    public void alarmManagerTest(Context context, Intent intent) {
        AlarmManagerTest test = new AlarmManagerTest();
        test.test(context, intent.getExtras());
        test.endTest(context);
    }
    @TestMe
    public void alarmPersist(Context context, Intent intent) {
        AlarmPersist test = new AlarmPersist(context, intent.getExtras());
        test.getAlarmPersist();
    }
    
    @TestMe
    public void Pm(Context context, Intent intent) {
        Pm pm = new Pm(context, intent);
        pm.list();
    }
    
    @TestMe
    public void Am(Context context, Intent intent) {
        Am am = new Am(context, intent.getExtras());
        am.test();
    }
    
    @TestMe
    public void screen(Context context, Intent intent) {
        ScreenTest test = new ScreenTest(context, intent);
        test.test();
    }
    
    
}
