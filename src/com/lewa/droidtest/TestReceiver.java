package com.lewa.droidtest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lewa.droidtest.alarm.AlarmManagerTest;
import com.lewa.droidtest.data.ParcelableTest;
import com.lewa.droidtest.pm.Pm;;
public class TestReceiver extends BroadcastReceiver{
    private static final String TAG = "TestReceiver";

    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestMe{}
    
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            final String action = getString(intent, "action", "ACTION!!");
            Log.e(TAG, "com.lewa.droidtest executing..." + action);
            Method actionMethod =  getClass().getMethod(action, Context.class, Intent.class);
            if(actionMethod.getAnnotation(TestMe.class) != null) {
                actionMethod.invoke(this, context,intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    private String getString(Intent intent, String key, String def) {
        String result = intent.getStringExtra(key);
        result = result == null ? def : result;
        return result;
    }
    
    @TestMe
    public void parcelable(Context context, Intent intent) {
        ParcelableTest.test();
    }
    
    @TestMe
    public void alarmManagerTest(Context context, Intent intent) {
        AlarmManagerTest test = new AlarmManagerTest();
        test.startTest(context, intent.getExtras());
        test.endTest(context);
    }
    
    @TestMe
    public void Pm(Context context, Intent intent) {
        Pm pm = new Pm(context, intent);
        pm.list();
    }
}
