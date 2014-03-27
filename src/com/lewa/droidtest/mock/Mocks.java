package com.lewa.droidtest.mock;

import android.content.Context;
import android.os.Bundle;

import com.lewa.droidtest.adb.SettingsTest;
import com.lewa.droidtest.alarm.AlarmManagerTest;
import com.lewa.droidtest.alarm.AlarmPersist;
import com.lewa.droidtest.am.Am;
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
import com.lewa.droidtest.screen.ScreenTest;
import com.lewa.droidtest.statusbar.StatusBarManagerTest;
import com.lewa.droidtest.systemui.DateViewMocker;
import com.lewa.droidtest.telephony.TelephonyTest;

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
        
        
        sModuleMap.put("settings", SettingsTest.class);
        sModuleMap.put("telephony", TelephonyTest.class);
        sModuleMap.put("ppm", PowerManagerTest.class);
        sModuleMap.put("screen", ScreenTest.class);
        
        sModuleMap.put("Am", Am.class);
        sModuleMap.put("Pm", Pm.class);
        sModuleMap.put("AlarmPersist", AlarmPersist.class);
        
        sModuleMap.put("dateView", DateViewMocker.class);

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
