package com.juude.droidmocks;

import android.app.Application;
import android.util.Log;

import com.juude.droidmocks.adb.SettingsMocker;
import com.juude.droidmocks.am.Am;
import com.juude.droidmocks.context.ContextMocker;
import com.juude.droidmocks.gps.GpsTest;
import com.juude.droidmocks.mock.Mocks;
import com.juude.droidmocks.pm.Pm;
import com.juude.droidmocks.provider.ProviderMocker;
import com.juude.droidmocks.stetho.StethoWrapper;

public class DroidMocksApplication extends Application{

    private static final String TAG = "DroidMocksApplication";

	@Override
    public void onCreate() {
        super.onCreate();
        StethoWrapper.onCreate(this);
        Mocks.sModuleMap.put("provider", ProviderMocker.class);
        Mocks.sModuleMap.put("gps", GpsTest.class);
        Mocks.sModuleMap.put("settings", SettingsMocker.class);
        Mocks.sModuleMap.put("Am", Am.class);
        Mocks. sModuleMap.put("Pm", Pm.class);
        Mocks.sModuleMap.put("context", ContextMocker.class);
        Log.d(TAG, "onCreate");
    }

}
