package com.juuda.droidmock.telephony;

import android.content.Context;
import android.os.Bundle;
import android.provider.Telephony.SIMInfo;
import android.util.Log;

import com.juuda.droidmock.mock.Mocker;

public class TelephonyTest extends Mocker{

    private static final String TAG = "Telephony";

    public TelephonyTest(Context context, Bundle extras) {
        super(context, extras);
    }

    @Override
    public void dump() {
        siminfo();
    }
    
    public void siminfo() {
        Log.e(TAG, "insertedSize: "  + SIMInfo.getInsertedSIMList(mContext).size());
    }
    
}
