package com.lewa.droidtest.power;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;

import com.lewa.droidtest.mock.Mocker;
/**
 * This requires android.permission.REBOOT
 * */
public class PowerManagerTest extends Mocker{
    private PowerManager mPm;
    public PowerManagerTest(Context context, Bundle extras) {
        super(context, extras);
        mPm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    }
    
    public void reboot() {
        mContext.enforceCallingOrSelfPermission(android.Manifest.permission.REBOOT, "no reboot");
        mPm.reboot(mExtras.getString("reason"));
    }

    @Override
    public void dump() {
        reboot();
    }
    
}
