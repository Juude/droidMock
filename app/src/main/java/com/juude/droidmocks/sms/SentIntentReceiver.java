
package com.juude.droidmocks.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.juude.droidmocks.Utils;

public class SentIntentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Utils.logE(this, "sent" + intent + getResultCode());
        Toast.makeText(context, "sent" + intent + getResultCode(), Toast.LENGTH_LONG)
                .show();

    }

}
