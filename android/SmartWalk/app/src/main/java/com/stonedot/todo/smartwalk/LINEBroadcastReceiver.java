package com.stonedot.todo.smartwalk;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by komatsu on 2016/10/29.
 */

public class LINEBroadcastReceiver {

    public interface LINEBroadcastReceiverListener {
        void onLINENotification(String sender, String content);
    }
    private LINEBroadcastReceiverListener mListener;

    private Activity mActivity;

    public LINEBroadcastReceiver(Activity activity, LINEBroadcastReceiverListener listener) {
        mActivity = activity;
        mListener = listener;
        notificationServiceStart();
    }

    private void notificationServiceStart() {
        Intent intent = new Intent(mActivity, LINENotificationService.class);
        mActivity.startService(intent);
        IntentFilter filter = new IntentFilter(LINENotificationService.PACKAGE_NAME);
        LocalBroadcastManager.getInstance(mActivity).registerReceiver(onNotice, filter);
    }

    private BroadcastReceiver onNotice = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String sender = intent.getStringExtra(LINENotificationService.KEY_SENDER);
            String content = intent.getStringExtra(LINENotificationService.KEY_CONTENT);
            if(mListener != null) mListener.onLINENotification(sender, content);
        }
    };
}
