
package com.juude.droidmocks.lock;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.RelativeLayout;

public class LockDialogService extends Service {
    private String TAG = "LockDialogService";

    private void addDialogView() {
        final RelativeLayout layout = new RelativeLayout(this);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setBackgroundColor(Color.GRAY);
            }
        });

        layout.setBackgroundColor(Color.RED);
        LayoutParams params;
        params = new LayoutParams();
        params.width = 200;
        params.height = 400;
        params.type = LayoutParams.TYPE_SYSTEM_DIALOG;
        // WindowManagerImpl.getDefault().addView(layout, params);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "starting..");
        addDialogView();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
