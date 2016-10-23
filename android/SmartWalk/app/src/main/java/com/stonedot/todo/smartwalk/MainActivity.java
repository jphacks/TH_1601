package com.stonedot.todo.smartwalk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
    implements LINESettingsFragment.LINESettingsListener {

    public SmartWalkFragmentManager mFM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFM = new SmartWalkFragmentManager(this);
        NotificationServiceAccess.showNotificationAccessSettingMenu(this);
    }
}
