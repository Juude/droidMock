package com.lewa.droidtest.mock;

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
import com.lewa.droidtest.mock.MockUtils.Mock;
import com.lewa.droidtest.network.HttpClientTest;
import com.lewa.droidtest.network.NetworkTest;
import com.lewa.droidtest.notification.NotificationCloud;
import com.lewa.droidtest.notification.NotificationMocker;
import com.lewa.droidtest.pm.Pm;
import com.lewa.droidtest.power.PowerManagerTest;
import com.lewa.droidtest.provider.ProviderTest;
import com.lewa.droidtest.am.Am;
import com.lewa.droidtest.screen.ScreenTest;
import com.lewa.droidtest.statusbar.StatusBarManagerTest;

import com.lewa.droidtest.telephony.TelephonyTest;

public class MockReceiver extends BroadcastReceiver{
    private static final String TAG = "TestReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {
        invoke(context, intent);
    }
   
    
    public void invoke(Context context, Intent intent) {
        try {
            final String action = MockUtils.getString(intent.getExtras(), "a", "ACTION!!");
            Log.e(TAG, "com.lewa.droidtest executing..." + action);
            Method actionMethod =  getClass().getMethod(action, Context.class, Intent.class);
            if(actionMethod.getAnnotation(MockUtils.Mock.class) != null) {
                actionMethod.invoke(this, context,intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    @Mock
    public void list(Context context, Intent intent) {
        Method[] methods = getClass().getMethods();
        for(Method method : methods) {
            if(method.isAnnotationPresent(Mock.class)) {
                Log.e(TAG, ": " + method.getName());
            }
        }
    }
    
    @Mock
    public void parcelable(Context context, Intent intent) {
        ParcelableTest.test();
    }
    
    @Mock
    public void alarmManagerTest(Context context, Intent intent) {
        AlarmManagerTest test = new AlarmManagerTest();
        test.test(context, intent.getExtras());
        test.endTest(context);
        
    }
    @Mock
    public void alarmPersist(Context context, Intent intent) {
        AlarmPersist test = new AlarmPersist(context, intent.getExtras());
        test.getAlarmPersist();
    }
    
    @Mock
    public void Pm(Context context, Intent intent) {
        Pm pm = new Pm(context, intent.getExtras());
        pm.test();
    }
    
    @Mock
    public void Am(Context context, Intent intent) {
        Am am = new Am(context, intent.getExtras());
        am.test();
    }
    
    @Mock
    public void screen(Context context, Intent intent) {
        ScreenTest test = new ScreenTest(context, intent);
        test.test();
    }
    
    @Mock
    public void ppm(Context context, Intent intent) {
        PowerManagerTest test = new PowerManagerTest(context, intent.getExtras());
        test.test();
    }
    
    @Mock
    public void telephony(Context context, Intent intent) {
        TelephonyTest test = new TelephonyTest(context, intent.getExtras());
        test.test();
    }
    
    @Mock
    public void settings(Context context, Intent intent) {
        SettingsTest test = new SettingsTest(context, intent.getExtras());
        test.test();
    }
    
    @Mock
    public void mms(Context context, Intent intent) {
        Mocker test = new MmsSenderTest(context, intent.getExtras());
        test.test();
    }
    
    @Mock
    public void network(Context context, Intent intent) {
        Mocker test = new NetworkTest(context, intent.getExtras());
        test.test();
    }
    
    @Mock
    public void statusbar(Context context, Intent intent) {
        Mocker test = new StatusBarManagerTest(context, intent.getExtras());
        test.test();
    }
    
    @Mock
    public void http(Context context, Intent intent) {
        Mocker test = new HttpClientTest(context, intent.getExtras());
        test.test();
    }
    
    @Mock
    public void gps(Context context, Intent intent) {
        Mocker test = new GpsTest(context, intent.getExtras());
        test.test();
    }
    
    @Mock
    public void provider(Context context, Intent intent) {
        Mocker test = new ProviderTest(context, intent.getExtras());
        test.test();
    }
    
    @Mock
    public void notification(Context context, Intent intent) {
        Mocker test = new NotificationMocker(context, intent.getExtras());
        test.test();
    }
    
    @Mock
    public void notificationCloud(Context context, Intent intent) {
        Mocker test = new NotificationCloud(context, intent.getExtras());
        test.test();
    }
}
