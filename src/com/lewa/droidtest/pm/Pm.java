package com.lewa.droidtest.pm;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;

import com.lewa.droidtest.test.TestUtils;

import java.util.List;

public class Pm {
    private Context mContext;
    private PackageManager mPm;
    private Bundle mArgs;
    private static final String TAG = "Pm";
    public Pm(Context context, Intent intent) {
        mContext = context;
        mPm = mContext.getPackageManager();
        mArgs = intent.getExtras();
    }
    public void list() {
        String packageName = mArgs.getString("package");
        Intent i = new Intent();
        if(packageName != null) {
            i.setPackage(packageName);
            try {
                PackageInfo pkgInfo = mPm.getPackageInfo(packageName, 
                        PackageManager.GET_RECEIVERS | 
                        PackageManager.GET_ACTIVITIES | 
                        PackageManager.GET_SERVICES);
                
                List<ResolveInfo> qReceivers = mPm.queryBroadcastReceivers(i, 0);
                for(ResolveInfo receiver : qReceivers) {
                    Log.e(TAG, "receiver:$ " + " $x$"+ receiver.activityInfo );
                }
                
                List<ResolveInfo> qActivities = mPm.queryIntentActivities(i, 0);
                for(ResolveInfo activity : qActivities) {
                    Log.e(TAG, "activity:$ " + " $x$"+ activity.activityInfo );
                }
                
                List<ResolveInfo> qServices = mPm.queryIntentServices(i, 0);
                for(ResolveInfo service : qServices) {
                    Log.e(TAG, "service:$ " + " $x$"+ service.activityInfo );
                }
                
            }
            catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void start() {
        if(mArgs.containsKey("package")) {
            String packageName = TestUtils.getString(mArgs, "package", "com.lewa.netmgr");
            Intent launchIntent = mContext.getPackageManager().getLaunchIntentForPackage(packageName);
            mContext.startActivity(launchIntent);
        }
        else {
            Log.e(TAG, "", new Throwable());
        }
    }
    public void test() {
        String method = TestUtils.getString(mArgs, "method", "list");
        try {
            Pm.class.getMethod(method).invoke(this);
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
    }
}
