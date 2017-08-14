package com.juude.droidmocks;
import android.app.Application;
import com.juude.droidmocks.context.ContextMocker;
import com.juude.droidmocks.gps.GpsTest;
import com.juude.droidmocks.mock.Mocks;
import com.juude.droidmocks.provider.ProviderMocker;
import com.juude.droidmocks.stetho.StethoWrapper;

public class DroidMocksApplication extends Application{

	@Override
    public void onCreate() {
        super.onCreate();
        StethoWrapper.onCreate(this);
        Mocks.sModuleMap.put("provider", ProviderMocker.class);
        Mocks.sModuleMap.put("gps", GpsTest.class);
        Mocks.sModuleMap.put("context", ContextMocker.class);
    }
}
