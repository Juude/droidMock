
package com.lewa.droidtest.statusbar;

import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.content.Context;
import com.android.internal.statusbar.IStatusBarService;

public class StatusBarTest {
    public Context mContext;
    public Bundle mBundle;
    
    private IStatusBarService mStatusBarService = null;

    public StatusBarTest(Context context, Bundle bundle) {
        mContext = context;
        mBundle = bundle;
    }
    
    public void test() {
        toggle();
    }
    
    public void toggle() {
        if (mStatusBarService == null) {
            mStatusBarService = IStatusBarService.Stub.asInterface(
                    ServiceManager.getService("statusbar"));
        }
        try {
            mStatusBarService.toggleRecentApps();
        }
        catch (RemoteException e) {
        }
    }
    
}
