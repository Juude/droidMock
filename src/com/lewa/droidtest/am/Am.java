
package com.lewa.droidtest.am;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lewa.droidtest.test.TestUtils;

import java.util.List;

public class Am {

    private static Context sContext;
    private final static String TAG = "Am";
    private Context mContext;
    private Bundle mBundle;
    
    public Am(Context context, Bundle bundle) {
        mContext = context;
        mBundle = bundle;
    }
    
    public void test() {
        startAll();
    }
    
    public void startAll() {
        int count = TestUtils.getInt(mBundle, "count", 15);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        List<ResolveInfo> infos = mContext.getPackageManager().queryIntentActivities(intent, 0);
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
