package com.juude.droidmocks.context;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;

import com.juude.droidmocks.mock.MockUtils;
import com.juude.droidmocks.mock.Mocker;

/**
 * TODO:
 * 1. to identify the android.R equals to com.android.internal.R or not
 * 2. newContext = createPackageContext..
 *    whether newContext can get the id and drawable if it not in the same process
 *    whether we can if it is in uid.system 
 * 3. getDrawable 
 *     1. use current context 
 *     2. use packageContext
 * */
public class ContextMocker extends Mocker {

    private static final String TAG = "ContextMocker";

    public ContextMocker(Context context, Bundle extras) {
        super(context, extras);
    }
    
    
    
    public void identifier() {
        int androidR = mContext.getResources().getIdentifier("stat_sys_adb", "drawable", "android");
        int internalAndroidR = mContext.getResources().getIdentifier("stat_sys_adb", "drawable", "com.android.internal");
        
        
        int androidR2 = mContext.getResources().getIdentifier("fillInIntent", "id", "android");
        int internalAndroidR2 = mContext.getResources().getIdentifier("fillInIntent", "id", "com.android.internal");
        
        Log.d(TAG, "androidR is " + androidR + " :: internalAndroidR is " + internalAndroidR + 
                "  :: equals ? "  + (androidR == internalAndroidR));
        
        Log.d(TAG, "androidR2 is " + androidR2 + " :: internalAndroidR2 is " + internalAndroidR2 + 
                "  :: equals ? "  + (androidR2 == internalAndroidR2));
        
        Context packageContext = null;
        try {
            packageContext = mContext.createPackageContext("org.x2ools", 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } 
        
        
        int adbIdBig = mContext.getResources().getIdentifier("stat_sys_data_usb_big", "drawable", "org.x2ools");
        int packageContextAdbIdBig = packageContext.getResources().getIdentifier("stat_sys_data_usb_big", "drawable", "org.x2ools");

        Bitmap packageContextBitmap = loadBitmap(packageContext, packageContextAdbIdBig);
        
        Log.d(TAG, "!     packageContextBitmap " + packageContextBitmap);
        
//        Bitmap contextBitmap = loadBitmap(mContext, adbIdBig);
//        Log.d(TAG, "contextBitmap : " + contextBitmap);

        Log.d(TAG, "stat_sys_tether_wifi id is " + mContext.getResources().getIdentifier("stat_sys_tether_wifi", "drawable", "android"));
        

    }
    
    private static  Bitmap loadBitmap(Context context, int id) {
        final BitmapDrawable bd = (BitmapDrawable)context.getResources().getDrawable(id);
        return Bitmap.createBitmap(bd.getBitmap());
    }
    
    
    @Override
    public void dump() {
        Log.d(TAG, "this is running in uid : " + android.os.Process.myUid());
        try {
            ContextMocker.class.getMethod(MockUtils.getString(mExtras, "nihao", "identifier"))
                .invoke(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
