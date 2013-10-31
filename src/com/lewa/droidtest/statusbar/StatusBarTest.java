
package com.lewa.droidtest.statusbar;

import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.content.Context;
import com.android.internal.statusbar.IStatusBarService;
import com.lewa.droidtest.test.Test;
import com.lewa.droidtest.test.TestUtils;

public class StatusBarTest extends Test{
    private static final String TAG = "StatusBarTest";
    public Context mContext;
    
    private IStatusBarService mStatusBarService = null;

    public StatusBarTest(Context context, Bundle bundle) {
        super(context, bundle);    }
    
    public void test() {
        String method = TestUtils.getString(mExtras, "method", "toggle");
        try {
            StatusBarTest.class.getMethod(method).invoke(this);
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
    }
    
    public void toggle() {
        if (mStatusBarService == null) {
            mStatusBarService = IStatusBarService.Stub.asInterface(
                    ServiceManager.getService("statusbar"));
            //mStatusBarService.disable(arg0, arg1, arg2);
        }
        try {
            mStatusBarService.toggleRecentApps();
        }
        catch (RemoteException e) {
        }
    }
    
    public void collapse() {
        if (mStatusBarService == null) {
            
            mStatusBarService = IStatusBarService.Stub.asInterface(
                    ServiceManager.getService("statusbar"));
            //mStatusBarService.disable(arg0, arg1, arg2);
        }
        try {
            mStatusBarService.collapsePanels();
        }
        catch (RemoteException e) {
        }
    }
    
    public void expand() {
        if (mStatusBarService == null) {
            mStatusBarService = IStatusBarService.Stub.asInterface(
                    ServiceManager.getService("statusbar"));
            //mStatusBarService.disable(arg0, arg1, arg2);
        }
        try {
            mStatusBarService.expandNotificationsPanel();
        }
        catch (RemoteException e) {
        }
    }
    
    
}
