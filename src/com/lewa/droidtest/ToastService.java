package com.lewa.droidtest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ToastService extends Service{

    private static final String TAG = "ToastService";


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent == null) {
            return  super.onStartCommand(intent, flags, startId);
        }
        String msg = intent.getStringExtra("message");
        Log.e(TAG, "msg : " + msg);
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }

    protected void onHandleIntent(Intent intent) {
        if(intent == null) {
            return;
        }
        String msg = intent.getStringExtra("message");
        Log.e(TAG, "msg : " + msg);
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
