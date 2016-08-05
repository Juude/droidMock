
package com.juuda.droidmock.am;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.juuda.droidmock.CommonActivity;
import com.juuda.droidmock.mock.MockUtils;
import com.juuda.droidmock.mock.Mocker;

import java.util.List;

public class Am extends Mocker{
    private PackageManager mPackageManager;
    public Am(Context context, Bundle extras) {
        super(context, extras);
        mPackageManager = context.getPackageManager();
    }

    private static Context sContext;
    private final static String TAG = "Am";

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
