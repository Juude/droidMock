
package com.juuda.droidmock.alarm;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.facebook.stetho.dumpapp.DumpException;
import com.facebook.stetho.dumpapp.DumperContext;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.juuda.droidmock.CommonActivity;
import com.juuda.droidmock.mock.MockUtils;
import com.juuda.droidmock.stetho.StethoHelper;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.PrintWriter;


public class AlarmPlugin implements DumperPlugin{
    private static final String TAG = "AlarmPlugin";
    private  Application mApplication;
    private BroadcastReceiver mReceiver;
    private PrintWriter mPrintWriter;

    private final String ALARM_ACTION = "com.android.deskclock.ALARM_ALERT";
    public AlarmPlugin(Application application) {
        mApplication = application;
    }

    public void cancelTask() {
        AlarmManager am = (AlarmManager)mApplication.getSystemService(Context.ALARM_SERVICE);
        PendingIntent operation = PendingIntent.getBroadcast(mApplication, 0, new Intent(ALARM_ACTION),
                PendingIntent.FLAG_UPDATE_CURRENT);
        operation.cancel();//works
        am.cancel(operation);//works
    }

    public void start(int delay) {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e(TAG, "received.. TAG");
                mPrintWriter.println("alarm received" + intent.getAction());
                mPrintWriter.close();
            }
        };
        mApplication.getApplicationContext().registerReceiver(mReceiver, new IntentFilter(ALARM_ACTION));
        AlarmManager am = (AlarmManager)mApplication.getSystemService(Context.ALARM_SERVICE);
        PendingIntent operation = PendingIntent.getBroadcast(mApplication, 0,
                new Intent(ALARM_ACTION),
                PendingIntent.FLAG_UPDATE_CURRENT);

        long currentMills = System.currentTimeMillis();
        am.set(AlarmManager.RTC_WAKEUP , currentMills + delay, operation);
    }

    @Override
    public String getName() {
        return "alarm";
    }

    private Options getOptions() {
        return new Options()
                .addOption("t", "type", true, "commnd type")
                .addOption("d", "delay", true, "delay");
    }

    @Override
    public void dump(DumperContext dumpContext) throws DumpException {
        mPrintWriter =  StethoHelper.getPrintWriter(dumpContext);
        try {
            CommandLine commandLine = StethoHelper.parse(dumpContext, getOptions());
            String type = commandLine.getOptionValue('t', "start");
            int delay = Integer.parseInt(commandLine.getOptionValue('d'));
            if ("start".equals(type)) {
                start(delay);
            } else if ("cancel".equals(type)) {
                cancelTask();
            }
        } catch (ParseException e) {
            mPrintWriter.println(Log.getStackTraceString(e));
        } finally {
            mPrintWriter.flush();
        }
    }

}