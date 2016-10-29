package com.stonedot.todo.smartwalk;

import android.app.Application;

import jp.line.android.sdk.LineSdkContextManager;

/**
 * Created by goto on 2016/10/29.
 */

public class SmartWalkApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // LineSDK を初期化
        LineSdkContextManager.initialize(this);
    }
}
