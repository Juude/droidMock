
package com.juude.droidmocks.am;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.stetho.dumpapp.DumpException;
import com.facebook.stetho.dumpapp.DumperContext;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.juude.droidmocks.CommonActivity;
import com.juude.droidmocks.mock.MockUtils;
import com.juude.droidmocks.mock.Mocker;
import com.juude.droidmocks.stetho.StethoHelper;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Am implements DumperPlugin {
    private final Application mContext;
    private PackageManager mPackageManager;
    public Am(Application application) {
        mContext = application;
        mPackageManager = application.getPackageManager();
    }

    private static Context sContext;
    private final static String TAG = "Am";

    public void anr(CommandLine commandLine) {
        final int timeout = StethoHelper.getInteger(commandLine, 't', 2000);
        try {
            for(int i = 0; i < timeout; i++) {
                Log.e(TAG, "i is " + i);
                Thread.sleep(1000);
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void anrFragment(CommandLine commandLine) {
        Log.e(TAG, "anrFragment");
        Intent i = new Intent(mContext, CommonActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(CommonActivity.KEY_FRAGMENT, "com.lewa.droidtest.am.AnrFragment");
        mContext.startActivity(i);
    }

    
    public void resolve(CommandLine commandLine) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        String packageName = StethoHelper.getString(commandLine, 'p', null);
        if (!TextUtils.isEmpty(packageName)) {
            intent.setPackage(packageName);
        }
        String action = StethoHelper.getString(commandLine, 'a', Intent.ACTION_MAIN);
        intent.setAction(action);

        String category = StethoHelper.getString(commandLine, "cat", Intent.CATEGORY_LAUNCHER);
        intent.addCategory(category);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        List<ResolveInfo> infos = mPackageManager.queryIntentActivities(intent, 0);
        
        for (int i= 0; i < infos.size(); i++) {
            ResolveInfo info = infos.get(i);
            Intent newIntent = new Intent(intent);
            newIntent.setComponent(new ComponentName(info.activityInfo.packageName,
                    info.activityInfo.name));
            Log.e(TAG, "newIntent : " + newIntent);
        }
        
    }
    
    public void startAll(CommandLine commandLine) {
        int count = StethoHelper.getInteger(commandLine, 'c', 15);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        List<ResolveInfo> infos = mContext.getPackageManager().queryIntentActivities(intent, 0);
        for (int i = 0; i < infos.size(); i++) {
            ResolveInfo info = infos.get(i);
            Intent newIntent = new Intent(intent);
            newIntent.setComponent(new ComponentName(info.activityInfo.packageName,
                    info.activityInfo.name));
            Message msg = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putParcelable("intent", newIntent);
            msg.setData(bundle);
            handler.sendMessageAtTime(msg, 1000 * i);
            if (i >= count)
                break;
        }
    }

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Intent i = msg.getData().getParcelable("intent");
            sContext.startActivity(i);
            Log.e(TAG, "starting.. .. #" + i);
            super.handleMessage(msg);
        }
    };

    @Override
    public String getName() {
        return "am";
    }

    @Override
    public void dump(DumperContext dumpContext) throws DumpException {
        PrintWriter printWriter = StethoHelper.getPrintWriter(dumpContext);
        try {
            CommandLine commandLine = StethoHelper.parse(dumpContext, getOptions());
            String method = StethoHelper.getString(commandLine, 'm', "startAll");
            Am.class.getMethod(method, CommandLine.class).invoke(this, commandLine);
        } catch (ParseException e) {
            printWriter.println(Log.getStackTraceString(e));
        } catch (IllegalAccessException e) {
            printWriter.println(Log.getStackTraceString(e));
        } catch (InvocationTargetException e) {
            printWriter.println(Log.getStackTraceString(e));
        } catch (NoSuchMethodException e) {
            printWriter.println(Log.getStackTraceString(e));
        }finally {
            printWriter.close();
        }
    }

    private Options getOptions() {
        return new Options()
                .addOption("c", "count", true, "the count")
                .addOption("time", "time", true, "the time")
                .addOption("a", "action", true, "action")
                .addOption("cat", "category", true, "the category")
                .addOption("m", "method", true, "method");
    }
}
