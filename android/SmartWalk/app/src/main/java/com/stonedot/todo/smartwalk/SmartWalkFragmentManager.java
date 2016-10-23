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

    private LINESettingsFragment mFragmentLINESettings;

    public SmartWalkFragmentManager(AppCompatActivity activity) {
        mActivity = activity;
        mFM = activity.getSupportFragmentManager();
        findFragments();
    }

    /**
     * フラグメントのインスタンスをメンバ変数として取得
     */
    private void findFragments() {
        mFragmentLINESettings = (LINESettingsFragment) mFM.findFragmentById(R.id.fragment_line_settings);
    }

    /**
     * フラグメントを入れ替える(フラグメント管理用クラスに分けたい)
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
