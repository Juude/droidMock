package com.juuda.droidmock.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;

import com.juuda.droidmock.mock.MockUtils;
import com.juuda.droidmock.mock.Mocker;

import lewa.telephony.LewaSimInfo;

public class TelephonyMocker extends Mocker{

    private static final String TAG = "Telephony";

    public TelephonyMocker(Context context, Bundle extras) {
        super(context, extras);
    }

    @Override
    public void dump() {
        try {
            TelephonyMocker.class.getMethod(MockUtils.getString(mExtras, "method", "simManager"))
            .invoke(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void simManager() {
        PackageManager pm = mContext.getPackageManager();
        Intent i = new Intent("com.android.settings.SIM_MANAGEMENT_ACTIVITY");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ResolveInfo info = pm.resolveActivity(i, PackageManager.MATCH_DEFAULT_ONLY);
        if(info != null) {
            mContext.startActivity(i);
        }
    }
    
    public void siminfo() {
        Log.e(TAG, "insertedSize: "  + LewaSimInfo.getInsertedSimList(mContext).size());
    }
    
}
