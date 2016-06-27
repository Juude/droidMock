
package com.juuda.droidmock.mms.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.juuda.droidmock.Utils;

public class DeliveryIntentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Utils.logE(this, "intent:" + intent + getResultCode());
        Toast.makeText(context, "intent:" + intent + getResultCode(), Toast.LENGTH_LONG)
                .show();
    }

}
