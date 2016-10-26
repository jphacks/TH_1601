package com.stonedot.todo.smartwalk;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by komatsu on 2016/10/23.
 */

public class SmartWalkFragmentManager {

    private AppCompatActivity mActivity;
    private FragmentManager mFM;
    private TextToSpeechManager mTTS;

    private LINESettingsFragment mFragmentLINESettings;

    public SmartWalkFragmentManager(AppCompatActivity activity) {
        mActivity = activity;
        mFM = mActivity.getSupportFragmentManager();
        mTTS = new TextToSpeechManager(mActivity);
        initFragments();
    }

    /**
     * フラグメントの初期化として、インスタンス取得とTTSの設定を行う
     * 対応SNSが増えたらここに追加していく
     */
    private void initFragments() {
        mFragmentLINESettings = (LINESettingsFragment) mFM.findFragmentById(R.id.fragment_line_settings);
        mFragmentLINESettings.setTextToSpeechManager(mTTS);
    }

    /**
     * フラグメントを入れ替える
     * @param rootViewID フラグメントを入れ替える対象となるViewのID
     * @param fragment rootViewIDの中身と入れ替えて表示するフラグメント
     */
    private void replaceFragment(int rootViewID, Fragment fragment) {
        FragmentTransaction transaction = mFM.beginTransaction();
        transaction.replace(rootViewID, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        mFM.executePendingTransactions();
    }

}
