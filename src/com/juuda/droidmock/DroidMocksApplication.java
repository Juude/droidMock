package com.juuda.droidmock;

import android.app.Application;

import com.juuda.droidmock.adb.SettingsMocker;
import com.juuda.droidmock.alarm.AlarmManagerTest;
import com.juuda.droidmock.alarm.AlarmPersist;
import com.juuda.droidmock.am.Am;
import com.juuda.droidmock.gps.GpsTest;
import com.juuda.droidmock.mock.Mocks;
import com.juuda.droidmock.network.HttpClientTest;
import com.juuda.droidmock.network.NetworkTest;
import com.juuda.droidmock.notification.NotificationCloud;
import com.juuda.droidmock.notification.NotificationMocker;
import com.juuda.droidmock.pm.Pm;
import com.juuda.droidmock.power.PowerManagerTest;
import com.juuda.droidmock.provider.ProviderTest;
import com.juuda.droidmock.screen.ScreenTest;
import com.juuda.droidmock.shortcut.ShortcutMocker;
import com.juuda.droidmock.statusbar.StatusBarManagerTest;
import com.juuda.droidmock.su.SuMocker;
import com.juuda.droidmock.telephony.TelephonyTest;

public class DroidMocksApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Mocks.sModuleMap.put("alarmManagerTest", AlarmManagerTest.class);
        Mocks.sModuleMap.put("notification", NotificationMocker.class);
        Mocks.sModuleMap.put("notificationCloud", NotificationCloud.class);
        Mocks.sModuleMap.put("provider", ProviderTest.class);
        Mocks.sModuleMap.put("gps", GpsTest.class);
        Mocks.sModuleMap.put("http", HttpClientTest.class);
        
        Mocks.sModuleMap.put("statusbar", StatusBarManagerTest.class);
        Mocks.sModuleMap.put("network", NetworkTest.class);
        Mocks.sModuleMap.put("http", HttpClientTest.class);
        
        
        Mocks.sModuleMap.put("settings", SettingsMocker.class);
        Mocks.sModuleMap.put("telephony", TelephonyTest.class);
        Mocks.sModuleMap.put("ppm", PowerManagerTest.class);
        Mocks.sModuleMap.put("screen", ScreenTest.class);
        
        Mocks.sModuleMap.put("Am", Am.class);
        Mocks. sModuleMap.put("Pm", Pm.class);
        Mocks. sModuleMap.put("AlarmPersist", AlarmPersist.class);
        
        Mocks.sModuleMap.put("shortcut", ShortcutMocker.class);
        
        Mocks.sModuleMap.put("su", SuMocker.class);
    }

}
