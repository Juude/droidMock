package com.juuda.droidmock.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.juuda.droidmock.R;
import com.juuda.droidmock.mock.MockUtils;
import com.juuda.droidmock.mock.Mocker;

import java.util.Random;

public class NotificationMocker extends Mocker{

    private static final String TAG = "NotificationMocker";

    public NotificationMocker(Context context, Bundle extras) {
        super(context, extras);
    }

    @Override
    public void dump() {
        try {
            NotificationMocker.class.getMethod(MockUtils.getString(mExtras, "method", "notification")).invoke(this);
        } catch (Exception e) {
            Log.e(TAG, "", e);
        }
    }
    private NotificationManager getNotificationManager() {
        return (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void notification() {
        int notifyId = new Random().nextInt(Integer.MAX_VALUE);
        Intent cancelSnooze = new Intent("hello");
        NotificationManager nm = getNotificationManager();
        CharSequence label = "mij" + notifyId;
        PendingIntent broadcast =
                PendingIntent.getBroadcast(mContext, notifyId, cancelSnooze, 0);

        Notification n = new Notification(R.drawable.ic_launcher,
                label , 0);
        
        n.setLatestEventInfo(mContext, label,
                "what is this", broadcast);
        n.flags |= Notification.FLAG_AUTO_CANCEL;
        n.flags |= Notification.FLAG_SHOW_LIGHTS;
        n.ledARGB = 0xFFFF0000;
        n.ledOnMS = 1000;
        n.ledOffMS = 500;
        
        nm.notify(notifyId, n);
    }
    
    public void notificationX() {
        Log.d(TAG, "notificationX");
        int notifyId = new Random().nextInt(Integer.MAX_VALUE);
        NotificationManager nm = getNotificationManager();
        Notification n = new Notification.Builder(mContext)
            .setContent(new RemoteViews(mContext.getPackageName(), R.layout.custom_notification))
            .setSmallIcon(R.drawable.ic_launcher)
            .build();
        nm.notify(notifyId, n);
    }

}
