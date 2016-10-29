package com.stonedot.todo.smartwalk;

import android.content.Intent;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;

/**
 * Created by komatsu on 2016/10/23.
 * LINEの通知クラス
 * こいつはインスタンス化できないので注意！
 * Broadcastによって外部に受け取った情報を伝える
 */

public class LINENotificationService extends SNSNotificationService {

    public static final String PACKAGE_NAME = "jp.naver.line.android";
    public static final String KEY_SENDER = "android.title";
    public static final String KEY_CONTENT = "android.text";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if(!sbn.getPackageName().equals(PACKAGE_NAME)) return;

        Bundle extras = sbn.getNotification().extras;
        Intent notification = new Intent(PACKAGE_NAME);
        notification.putExtra(KEY_SENDER, extras.getString(KEY_SENDER));
        notification.putExtra(KEY_CONTENT, extras.getCharSequence(KEY_CONTENT).toString());

        broadcast(notification);
    }
}
