package com.stonedot.todo.smartwalk;

import android.content.Context;
import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by komatsu on 2016/10/29.
 */

public class SNSNotificationService extends NotificationListenerService {

    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
    }

    protected void broadcast(Intent notification) {
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(notification);
    }
}
