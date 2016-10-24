package com.stonedot.todo.smartwalk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by komatsu on 2016/10/23.
 */

public class LINENotificationService extends NotificationListenerService {

    public static final String PACKAGE_NAME = "jp.naver.line.android";
    public static final String KEY_SENDER = "android.title";
    public static final String KEY_CONTENT = "android.text";

    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if(!sbn.getPackageName().equals(PACKAGE_NAME)) return;

        Bundle extras = sbn.getNotification().extras;
        Intent msgrcv = new Intent(PACKAGE_NAME);

        msgrcv.putExtra(KEY_SENDER, extras.getString(KEY_SENDER));
        msgrcv.putExtra(KEY_CONTENT, extras.getCharSequence(KEY_CONTENT).toString());

        LocalBroadcastManager.getInstance(mContext).sendBroadcast(msgrcv);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
    }
}
