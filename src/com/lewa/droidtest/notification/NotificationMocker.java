package com.lewa.droidtest.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lewa.droidtest.R;
import com.lewa.droidtest.mock.Mocker;

public class NotificationMocker extends Mocker{

    public NotificationMocker(Context context, Bundle extras) {
        super(context, extras);
    }

    @Override
    public void test() {
        notification();
    }
    private NotificationManager getNotificationManager() {
        return (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void notification() {
        int notifyId = 1000;
        Intent cancelSnooze = new Intent("hello");
        NotificationManager nm = getNotificationManager();
        CharSequence label = "mij";
        PendingIntent broadcast =
                PendingIntent.getBroadcast(mContext, notifyId, cancelSnooze, 0);

        Notification n = new Notification(R.drawable.ic_launcher,
                label , 0);
        n.setLatestEventInfo(mContext, label,
                "what is this", broadcast);
        n.flags |= Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_ONGOING_EVENT;
        nm.notify(notifyId, n);
    }

}
