package com.juude.droidmocks.power;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.stetho.dumpapp.DumpException;
import com.facebook.stetho.dumpapp.DumperContext;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.juude.droidmocks.mock.MockUtils;
import com.juude.droidmocks.mock.Mocker;
import com.juude.droidmocks.stetho.StethoHelper;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.PrintWriter;

/**
 * This requires android.permission.REBOOT
 * */
public class PowerManagerMocker implements DumperPlugin {
    private static final String TAG = "PowerManagerMocker";
    private final Application application;
    private final PowerManager mPm;

    public PowerManagerMocker(Application application) {
        this.application = application;
        mPm = (PowerManager) application.getSystemService(Context.POWER_SERVICE);
    }
    
    public void reboot() {
        application.enforceCallingOrSelfPermission(android.Manifest.permission.REBOOT, "no reboot");
    }
    
    public void wake() {
        Log.d(TAG, "wake");
        WakeLock wakeLock = mPm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "hehe");
        wakeLock.acquire(5000);
        wakeLock.release();
    }

    @Override
    public String getName() {
        return "power";
    }

    @Override
    public void dump(DumperContext dumpContext) throws DumpException {
        PrintWriter printWriter = StethoHelper.getPrintWriter(dumpContext);
        try {
            CommandLine commandLine = StethoHelper.parse(dumpContext, getOptions());
            String action = StethoHelper.getString(commandLine, 'a', "reboot");
            if (TextUtils.equals(action, "reboot")) {
                reboot();
            } else {
                wake();
            }
        } catch (ParseException e) {
            printWriter.println(Log.getStackTraceString(e));
        } finally {
            printWriter.close();
        }
    }

    private Options getOptions() {
        return new Options()
                .addOption("a", "action", true, "the action");
    }


}
