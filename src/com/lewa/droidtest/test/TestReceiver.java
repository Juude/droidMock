package com.lewa.droidtest.test;

import java.lang.reflect.Method;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lewa.droidtest.adb.SettingsTest;
import com.lewa.droidtest.alarm.AlarmManagerTest;
import com.lewa.droidtest.alarm.AlarmPersist;
import com.lewa.droidtest.data.ParcelableTest;
import com.lewa.droidtest.gps.GpsTest;
import com.lewa.droidtest.mms.MmsSenderTest;
import com.lewa.droidtest.network.HttpClientTest;
import com.lewa.droidtest.network.NetworkTest;
import com.lewa.droidtest.pm.Pm;
import com.lewa.droidtest.power.PowerManagerTest;
import com.lewa.droidtest.am.Am;
import com.lewa.droidtest.screen.ScreenTest;
import com.lewa.droidtest.statusbar.StatusBarTest;

import com.lewa.droidtest.telephony.TelephonyTest;
import com.lewa.droidtest.test.TestUtils.TestMe;

public class TestReceiver extends BroadcastReceiver{
    private static final String TAG = "TestReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {
        invoke(context, intent);
    }
   
    
    public void invoke(Context context, Intent intent) {
        try {
            final String action = TestUtils.getString(intent.getExtras(), "action", "ACTION!!");
            Log.e(TAG, "com.lewa.droidtest executing..." + action);
            Method actionMethod =  getClass().getMethod(action, Context.class, Intent.class);
            if(actionMethod.getAnnotation(TestUtils.TestMe.class) != null) {
                actionMethod.invoke(this, context,intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
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
        Pm pm = new Pm(context, intent.getExtras());
        pm.test();
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
    
    @TestMe
    public void ppm(Context context, Intent intent) {
        PowerManagerTest test = new PowerManagerTest(context, intent.getExtras());
        test.test();
    }
    
    @TestMe
    public void telephony(Context context, Intent intent) {
        TelephonyTest test = new TelephonyTest(context, intent.getExtras());
        test.test();
    }
    
    @TestMe
    public void settings(Context context, Intent intent) {
        SettingsTest test = new SettingsTest(context, intent.getExtras());
        test.test();
    }
    
    @TestMe
    public void mms(Context context, Intent intent) {
        Test test = new MmsSenderTest(context, intent.getExtras());
        test.test();
    }
    
    @TestMe
    public void network(Context context, Intent intent) {
        Test test = new NetworkTest(context, intent.getExtras());
        test.test();
    }
    
    @TestMe
    public void statusbar(Context context, Intent intent) {
        Test test = new StatusBarTest(context, intent.getExtras());
        test.test();
    }
    
    @TestMe
    public void http(Context context, Intent intent) {
        Test test = new HttpClientTest(context, intent.getExtras());
        test.test();
    }
    
    @TestMe
    public void gps(Context context, Intent intent) {
        Test test = new GpsTest(context, intent.getExtras());
        test.test();
    }
}
