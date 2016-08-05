package com.juude.droidmocks.power;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

import com.juude.droidmocks.mock.MockUtils;
import com.juude.droidmocks.mock.Mocker;

/**
 * This requires android.permission.REBOOT
 * */
public class PowerManagerMocker extends Mocker {
    private static final String TAG = "PowerManagerMocker";
    
    private PowerManager mPm;
    public PowerManagerMocker(Context context, Bundle extras) {
        super(context, extras);
        mPm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    }
    
    public void reboot() {
        mContext.enforceCallingOrSelfPermission(android.Manifest.permission.REBOOT, "no reboot");
        mPm.reboot(mExtras.getString("reason"));
    }
    
    public void wake() {
        Log.d(TAG, "wake");
        WakeLock wakeLock = mPm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "hehe");
        wakeLock.acquire(5000);
        wakeLock.release();
    }
    
    @Override
    public void dump() {
        try {
            PowerManagerMocker.class.getMethod(MockUtils.getString(mExtras, "method", "wake"))
            .invoke(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
   }
    
}
