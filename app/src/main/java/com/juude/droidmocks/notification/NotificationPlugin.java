package com.juude.droidmocks.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.facebook.stetho.dumpapp.DumpException;
import com.facebook.stetho.dumpapp.DumperContext;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.juuda.droidmock.R;
import com.juude.droidmocks.mock.MockUtils;
import com.juude.droidmocks.mock.Mocker;

import java.util.Random;

public class NotificationPlugin implements DumperPlugin {

    private static final String TAG = "NotificationPlugin";
    private final Context context;

    public NotificationPlugin(Context context) {
        this.context = context;
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void notificationx() {
        int notifyId = new Random().nextInt(Integer.MAX_VALUE);
        Intent cancelSnooze = new Intent("hello");
        NotificationManager nm = getNotificationManager();
        CharSequence label = "mij" + notifyId;
        PendingIntent broadcast =
                PendingIntent.getBroadcast(context, notifyId, cancelSnooze, 0);

        Notification n = new Notification(R.drawable.ic_launcher,
                label , 0);
        n.flags |= Notification.FLAG_AUTO_CANCEL;
        n.flags |= Notification.FLAG_SHOW_LIGHTS;
        n.ledARGB = 0xFFFF0000;
        n.ledOnMS = 1000;
        n.ledOffMS = 500;
        
        nm.notify(notifyId, n);
    }

    @Override
    public String getName() {
        return "notification";
    }

    @Override
    public void dump(DumperContext dumpContext) throws DumpException {
        notificationx();
    }
}
