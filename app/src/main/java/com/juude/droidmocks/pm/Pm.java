package com.juude.droidmocks.pm;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
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
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Pm implements DumperPlugin {
    private final Application mContext;
    private PackageManager mPm;
    private static final String TAG = "Pm";
    private PrintWriter mPrintWriter;
    public Pm(Application application) {
        mPm = application.getPackageManager();
        mContext = application;
    }
    
    public String list(CommandLine commandLine) {
        StringBuilder builder = new StringBuilder();
        String packageName = StethoHelper.getString(commandLine, 'p', null);
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }
        Intent i = new Intent();
        if(packageName != null) {
            i.setPackage(packageName);
            try {
                PackageInfo pkgInfo = mPm.getPackageInfo(packageName,
                        PackageManager.GET_RECEIVERS | 
                        PackageManager.GET_ACTIVITIES | 
                        PackageManager.GET_SERVICES);
                ApplicationInfo appInfo = pkgInfo.applicationInfo;
                
                List<ResolveInfo> qReceivers = mPm.queryBroadcastReceivers(i, 0);
                for(ResolveInfo receiver : qReceivers) {
                    builder.append("receiver:$ " + " $x$"+ receiver.activityInfo + "\n");
                }
                
                List<ResolveInfo> qActivities = mPm.queryIntentActivities(i, 0);
                for(ResolveInfo activity : qActivities) {
                    builder.append("activity:$ " + " $x$"+ activity.activityInfo + "\n");
                }
                
                List<ResolveInfo> qServices = mPm.queryIntentServices(i, 0);
                for(ResolveInfo service : qServices) {
                    builder.append("service:$ " + " $x$"+ service.activityInfo + "\n");
                }
                
                builder.append("installLocation : " + pkgInfo.installLocation);
               // builder.append("getLockedZipFilePath : " + pkgInfo.getLockedZipFilePath());
                builder.append("sourcedir : " + appInfo.sourceDir);
                builder.append("publicSourceDir : " + appInfo.publicSourceDir);

            }
            catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        mPrintWriter.println("str : " + builder.toString());
        return builder.toString();
    }
    
    public void start(CommandLine commandLine) {
        String packageName = StethoHelper.getString(commandLine, 'p', null);
        if (!TextUtils.isEmpty(packageName)) {
            Intent launchIntent = mContext.getPackageManager().getLaunchIntentForPackage(packageName);
            mContext.startActivity(launchIntent);
        }

    }

    @Override
    public String getName() {
        return "pm";
    }

    @Override
    public void dump(DumperContext dumpContext) throws DumpException {
        PrintWriter printWriter = StethoHelper.getPrintWriter(dumpContext);
        mPrintWriter = printWriter;
        try {
            CommandLine commandLine = StethoHelper.parse(dumpContext, getOptions());
            String action = StethoHelper.getString(commandLine, 'a', "start");
            Pm.class.getMethod(action, CommandLine.class).invoke(this, commandLine);
        } catch (ParseException e) {
            printWriter.println(Log.getStackTraceString(e));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }finally {
            printWriter.flush();
        }
    }

    private Options getOptions() {
        return new Options()
                .addOption("p", "package", true, "the package")
                .addOption("a", "action", true, "the action");
    }
}
