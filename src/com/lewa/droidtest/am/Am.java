
package com.lewa.droidtest.am;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;

import com.lewa.droidtest.CommonActivity;
import com.lewa.droidtest.mock.Mocker;
import com.lewa.droidtest.mock.MockUtils;

import java.util.List;

public class Am extends Mocker{
    private PackageManager mPackageManager;
    private IActivityManager mAm;
    public Am(Context context, Bundle extras) {
        super(context, extras);
        mPackageManager = context.getPackageManager();
        mAm = ActivityManagerNative.getDefault();
    }

    private static Context sContext;
    private final static String TAG = "Am";
    
    public void forceStop() {
        final String pkgName  = MockUtils.getString(mExtras, "package", "com.lewa.weather");
        try {
            mAm.forceStopPackage(pkgName, UserHandle.myUserId());
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    
    public void killProcess() {
        final String pkgName  = MockUtils.getString(mExtras, "package", "com.lewa.weather");
        try {
            
            List<RunningAppProcessInfo> runningProcesses = mAm.getRunningAppProcesses();
            for(RunningAppProcessInfo process : runningProcesses) {
                final String [] packages = mPackageManager.getPackagesForUid(process.uid);
                if(packages != null && packages[0].equals(pkgName)) {
                    android.os.Process.killProcess(process.pid);
                }
            }
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    
    public void killBackground() {
        final String pkgName  = MockUtils.getString(mExtras, "package", "com.lewa.weather");
        Log.e(TAG, "killing " + pkgName);
        try {
            Log.e(TAG, "killing " + pkgName);
            mAm.killBackgroundProcesses(pkgName, UserHandle.myUserId());
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    
    public void anr() {
        final int timeout = MockUtils.getInt(mExtras, "timeout", 20);
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
    
    public void anrFragment() {
        Log.e(TAG, "anrFragment");
        Intent i = new Intent(mContext, CommonActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(CommonActivity.KEY_FRAGMENT, "com.lewa.droidtest.am.AnrFragment");
        mContext.startActivity(i);
    }
    
    public void dump() {
        try {
            Am.class.getMethod(MockUtils.getString(mExtras, "method", "resolve")).invoke(this);
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
    }
    
    public void resolve() {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        if(mExtras.containsKey("package")) {
            String packageName = MockUtils.getString(mExtras, "package", "com.lewa.netmgr");
            intent.setPackage(packageName);
        }
        if(mExtras.containsKey("action")) {
            String action = MockUtils.getString(mExtras, "action", Intent.ACTION_MAIN);
            intent.setAction(action);
        }
        if(mExtras.containsKey("category")) {
            String category = MockUtils.getString(mExtras, "category", Intent.CATEGORY_LAUNCHER);
            intent.addCategory(category);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        /*
        if(mExtras.containsKey("flag")) {
            String category = TestUtils.getString(mExtras, "flag", Intent.flag_activity_new);
            i.addCategory(category);
        }
        */
        List<ResolveInfo> infos = mPackageManager.queryIntentActivities(intent, 0);
        
        for (int i= 0; i < infos.size(); i++) {
            ResolveInfo info = infos.get(i);
            Intent newIntent = new Intent(intent);
            newIntent.setComponent(new ComponentName(info.activityInfo.packageName,
                    info.activityInfo.name));
            Log.e(TAG, "newIntent : " + newIntent);
        }
        
    }
    
    public void startAll() {
        int count = MockUtils.getInt(mExtras, "count", 15);
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
}
