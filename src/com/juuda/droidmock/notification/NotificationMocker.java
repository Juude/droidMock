package com.juuda.droidmock.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.juuda.droidmock.R;
import com.juuda.droidmock.mock.Mocker;

import java.util.Random;

public class NotificationMocker extends Mocker{

    public NotificationMocker(Context context, Bundle extras) {
        super(context, extras);
    }

    @Override
    public void dump() {
        notification();
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
        nm.notify(notifyId, n);
    }

}
