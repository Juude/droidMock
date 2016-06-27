package com.juuda.droidmock.pm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadcastReceiverTest extends BroadcastReceiver {

    private static final String TAG = "BroadcastReceiverTest";

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        Log.e(TAG, "I'm received");
    }

}
