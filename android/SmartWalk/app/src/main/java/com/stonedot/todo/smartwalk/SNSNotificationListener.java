package com.stonedot.todo.smartwalk;

/**
 * Created by komatsu on 2016/10/28.
 */

public interface SNSNotificationListener {
    enum SNS {LINE, FACEBOOK, TWITTER}

    void onNotify(SNS sns);
}
