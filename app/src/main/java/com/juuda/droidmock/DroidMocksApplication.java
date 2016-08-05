package com.juuda.droidmock;

import android.app.Application;
import android.util.Log;

import com.juuda.droidmock.adb.SettingsMocker;
import com.juuda.droidmock.alarm.AlarmPlugin;
import com.juuda.droidmock.am.Am;
import com.juuda.droidmock.context.ContextMocker;
import com.juuda.droidmock.display.DisplayMocker;
import com.juuda.droidmock.featureoption.FeatureOptionMocker;
import com.juuda.droidmock.gps.GpsTest;
import com.juuda.droidmock.mock.Mocks;
import com.juuda.droidmock.network.NetworkMocker;
import com.juuda.droidmock.notification.NotificationCloud;
import com.juuda.droidmock.notification.NotificationMocker;
import com.juuda.droidmock.pm.Pm;
import com.juuda.droidmock.power.PowerManagerMocker;
import com.juuda.droidmock.provider.ProviderTest;
import com.juuda.droidmock.shortcut.ShortcutMocker;
import com.juuda.droidmock.stetho.StethoHelper;
import com.juuda.droidmock.stetho.StethoWrapper;
import com.juuda.droidmock.su.SuMocker;

public class DroidMocksApplication extends Application{

    private static final String TAG = "DroidMocksApplication";

	@Override
    public void onCreate() {
        super.onCreate();
        StethoWrapper.onCreate(this);
        Mocks.sModuleMap.put("notification", NotificationMocker.class);
        Mocks.sModuleMap.put("notificationCloud", NotificationCloud.class);
        Mocks.sModuleMap.put("provider", ProviderTest.class);
        Mocks.sModuleMap.put("gps", GpsTest.class);
        Mocks.sModuleMap.put("network", NetworkMocker.class);
        Mocks.sModuleMap.put("settings", SettingsMocker.class);
        Mocks.sModuleMap.put("ppm", PowerManagerMocker.class);
        Mocks.sModuleMap.put("display", DisplayMocker.class);
        Mocks.sModuleMap.put("Am", Am.class);
        Mocks. sModuleMap.put("Pm", Pm.class);
        Mocks.sModuleMap.put("shortcut", ShortcutMocker.class);
        Mocks.sModuleMap.put("su", SuMocker.class);
        Mocks.sModuleMap.put("featureoption", FeatureOptionMocker.class);
        Mocks.sModuleMap.put("context", ContextMocker.class);
        Log.d(TAG, "onCreate");
    }

}
