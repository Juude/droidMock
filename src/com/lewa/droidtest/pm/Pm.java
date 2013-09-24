package com.lewa.droidtest.pm;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;

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
    public void list(){
        if(mArgs.containsKey("broadcast")) {
            String packageName = mArgs.getString("package");
            Intent i = new Intent();
            if(packageName != null) {
                i.setPackage(packageName);
                try {
                    PackageInfo pkgInfo = mPm.getPackageInfo(packageName, 
                            PackageManager.GET_RECEIVERS | 
                            PackageManager.GET_ACTIVITIES | 
                            PackageManager.GET_SERVICES);
                    ActivityInfo[] receivers = pkgInfo.receivers;
                    for(ActivityInfo receiver : receivers) {
                        Log.e(TAG, "receiver:$ " +  receiver.isEnabled()  + " $x$"+ receiver.name );
                    }
                    List<ResolveInfo> qReceivers = mPm.queryBroadcastReceivers(i, 0);
                    for(ResolveInfo receiver : qReceivers) {
                        Log.e(TAG, "receiver:$ " + " $x$"+ receiver.activityInfo );
                    }
                }
                catch (NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
