
package com.juude.droidmocks.provider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class ProviderTestReceiver extends BroadcastReceiver {

    private static final String TAG = "ProviderTestReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Cursor cursor = context.getContentResolver().query(
                Uri.parse("content://com.lewa.droidtest/"), null, null, null, null);
        cursor.moveToFirst();
        do
        {
            Log.e(TAG, "set " + cursor.getString(0));
        }
        while (cursor.moveToNext());
    }

}
