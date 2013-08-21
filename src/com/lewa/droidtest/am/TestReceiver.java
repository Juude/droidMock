
package com.lewa.droidtest.am;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.List;

public class TestReceiver extends BroadcastReceiver {
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestMe {
    }

    private static Context sContext;
    private final static String TAG = "TestReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getStringExtra("action");
        sContext = context;
        try {
            Method actionMethod = getClass().getMethod(action, Context.class, Intent.class);
            if (actionMethod.getAnnotation(TestMe.class) != null) {
                actionMethod.invoke(this, context, intent);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getStringExtra(Intent intent, String key, String def) {
        String value = intent.getStringExtra(key);
        if (value == null) {
            value = def;
        }
        return value;
    }

    public int getIntExtra(Intent intent, String key, int def) {
        int intValue = 0;
        String value = intent.getStringExtra(key);
        if (value == null) {
            intValue = def;
        }
        else
        {
            intValue = Integer.parseInt(value);
        }
        return intValue;
    }

    @TestMe
    public void startAll(Context context, Intent intentt) {
        int count = getIntExtra(intentt, "count", 15);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        List<ResolveInfo> infos = context.getPackageManager().queryIntentActivities(intent, 0);
        for (int i = 0; i < infos.size(); i++) {
            ResolveInfo info = infos.get(i);
            Intent newIntent = new Intent(intent);
            newIntent.setComponent(new ComponentName(info.activityInfo.packageName,
                    info.activityInfo.name));
            Message msg = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putParcelable("intent", newIntent);
            msg.setData(bundle);
            handler.sendMessageAtTime(msg, 1000 * i);
            if (i >= count)
                break;
        }
    }

    @TestMe
    public void getSettings(Context context, Intent intentt) {
        int value = Settings.Global.getInt(context.getContentResolver(),
                Settings.Global.SET_INSTALL_LOCATION,
                0);
        Log.e(TAG, "SET_INSTALL_LOCATION : " + value);
    }

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Intent i = msg.getData().getParcelable("intent");
            sContext.startActivity(i);
            Log.e(TAG, "starting.. .. #" + i);
            super.handleMessage(msg);
        }
    };
}
