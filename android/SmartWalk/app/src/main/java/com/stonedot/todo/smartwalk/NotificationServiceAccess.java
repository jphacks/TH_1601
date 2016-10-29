package com.stonedot.todo.smartwalk;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.provider.Settings;

/**
 * Created by komatsu on 2016/10/23.
 * このアプリで通知を受け取れるように設定画面を開くためのクラス
 */

public class NotificationServiceAccess {

    /**
     * このアプリが通知を受け取れるか確認
     * @return このアプリが通知を受け取れるか
     */
    public static boolean isEnabledReadNotification(Activity activity) {
        ContentResolver contentResolver = activity.getContentResolver();
        String rawListeners = Settings.Secure.getString(contentResolver, "enabled_notification_listeners");
        if (rawListeners == null || "".equals(rawListeners)) return false;
        String[] listeners = rawListeners.split(":");
        for (String listener : listeners) {
            if (listener.startsWith(activity.getPackageName()))
                return true;
        }
        return false;
    }

    /**
     * このアプリに通知を有効にさせる設定画面を表示
     * すでに有効になっていれば何もせず終了
     */
    public static void showNotificationAccessSettingMenu(Activity activity) {
        if(isEnabledReadNotification(activity)) return;
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
        activity.startActivity(intent);
    }
}
