
package com.lewa.droidtest.statusbar;

import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.app.StatusBarManager;
import android.content.Context;
import com.android.internal.statusbar.IStatusBarService;
import com.lewa.droidtest.mock.Mocker;
import com.lewa.droidtest.mock.MockUtils;

public class StatusBarManagerTest extends Mocker{
    private static final String TAG = "StatusBarTest";
    
    private IStatusBarService mStatusBarService = null;
    private StatusBarManager mStatusBarManager = null;

    public StatusBarManagerTest(Context context, Bundle bundle) {
        super(context, bundle);    
        mStatusBarManager = (StatusBarManager) context.getSystemService(Context.STATUS_BAR_SERVICE);
    }
    
    
    public void test() {
        String method = MockUtils.getString(mExtras, "method", "toggle");
        try {
            StatusBarManagerTest.class.getMethod(method).invoke(this);
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
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
    
    public void disable() {
        int disableFlag = MockUtils.getInt(mExtras, "disable", StatusBarManager.DISABLE_CLOCK);
        mStatusBarManager.disable(disableFlag);
    }
    
    public void collapse() {
        if (mStatusBarService == null) {
            
            mStatusBarService = IStatusBarService.Stub.asInterface(
                    ServiceManager.getService("statusbar"));
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
        }
        try {
            mStatusBarService.expandNotificationsPanel();
        }
        catch (RemoteException e) {
        }
    }
    
    public void background() {
        if (mStatusBarService == null) {
            mStatusBarService = IStatusBarService.Stub.asInterface(
                    ServiceManager.getService("statusbar"));
        }
        try {
            mStatusBarService.expandNotificationsPanel();
        }
        catch (RemoteException e) {
        }
    }
    
    public void clock() {
        ClockInjection test = new ClockInjection();
        test.test(mContext);
    }
    
}
