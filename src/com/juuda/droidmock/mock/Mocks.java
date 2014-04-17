package com.juuda.droidmock.mock;

import android.content.Context;
import android.os.Bundle;

import com.juuda.droidmock.adb.SettingsMocker;
import com.juuda.droidmock.alarm.AlarmManagerTest;
import com.juuda.droidmock.alarm.AlarmPersist;
import com.juuda.droidmock.am.Am;
import com.juuda.droidmock.gps.GpsTest;
import com.juuda.droidmock.mms.MmsSenderTest;
import com.juuda.droidmock.mock.MockUtils.Mock;
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
import com.juuda.droidmock.systemui.DateViewMocker;
import com.juuda.droidmock.telephony.TelephonyTest;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public class Mocks {
    private static Mocks sInstance = null;
   
    public static Mocks getInstance() {
        synchronized(Mocks.class) {
            if(sInstance == null) {
                sInstance = new Mocks();
            }
            return sInstance;
        }
    }
    
    private Mocks() {
      
    }
    
    public static HashMap<String, Class<? extends Mocker>> sModuleMap = new HashMap<String, Class<? extends Mocker>>();
    
    static {
        sModuleMap.put("alarmManagerTest", AlarmManagerTest.class);
        sModuleMap.put("notification", NotificationMocker.class);
        sModuleMap.put("notificationCloud", NotificationCloud.class);
        sModuleMap.put("provider", ProviderTest.class);
        sModuleMap.put("gps", GpsTest.class);
        sModuleMap.put("http", HttpClientTest.class);
        
        sModuleMap.put("statusbar", StatusBarManagerTest.class);
        sModuleMap.put("network", NetworkTest.class);
        sModuleMap.put("mms", MmsSenderTest.class);
        sModuleMap.put("http", HttpClientTest.class);
        
        
        sModuleMap.put("settings", SettingsMocker.class);
        sModuleMap.put("telephony", TelephonyTest.class);
        sModuleMap.put("ppm", PowerManagerTest.class);
        sModuleMap.put("screen", ScreenTest.class);
        
        sModuleMap.put("Am", Am.class);
        sModuleMap.put("Pm", Pm.class);
        sModuleMap.put("AlarmPersist", AlarmPersist.class);
        
        sModuleMap.put("dateView", DateViewMocker.class);
        sModuleMap.put("shortcut", ShortcutMocker.class);
        
        sModuleMap.put("su", SuMocker.class);

    }
    
    
    @Mock
    public void dump(Context context, Bundle bundle) {
        final String module = MockUtils.getString(bundle, "notification", "notification");
        Class<? extends Mocker> clazz = sModuleMap.get(module);    
        try {
            Constructor<? extends Mocker> mockerConstructor = clazz.getConstructor(Context.class, Bundle.class);
            Mocker mocker = (Mocker) mockerConstructor.newInstance(context, bundle);
            mocker.dump();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
