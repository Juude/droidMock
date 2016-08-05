package com.juude.droidmocks.pm;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;

import com.juude.droidmocks.mock.MockUtils;
import com.juude.droidmocks.mock.Mocker;

import java.util.List;

public class Pm extends Mocker {
    private PackageManager mPm;
    private static final String TAG = "Pm";
    
    public Pm(Context context, Bundle extras) {
        super(context, extras);
        mPm = mContext.getPackageManager();
    }
    
    public String list() {
        StringBuilder builder = new StringBuilder();
        String packageName = mExtras.getString("package");
        Intent i = new Intent();
        if(packageName != null) {
            i.setPackage(packageName);
            try {
                /*TODO: why pkgInfo does not work?*/
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
        Log.e(TAG, "string" + builder.toString());
        return builder.toString();
    }
    
    public void start() {
        if(mExtras.containsKey("package")) {
            String packageName = MockUtils.getString(mExtras, "package", "com.lewa.netmgr");
            Intent launchIntent = mContext.getPackageManager().getLaunchIntentForPackage(packageName);
            mContext.startActivity(launchIntent);
        }
        else {
            Log.e(TAG, "", new Throwable());
        }
    }

    @Override
    public void dump() {
        String method = MockUtils.getString(mExtras, "method", "list");
        try {
            Pm.class.getMethod(method).invoke(this);
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
    }
}
