package com.stonedot.todo.smartwalk;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
    implements LINESettingsFragment.LINESettingsListener {

    private FragmentManager mFM;
    private LINESettingsFragment mFragmentLINESettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findFragments();
    }

    /**
     * フラグメントのインスタンスをメンバ変数として取得
     */
    private void findFragments() {
        mFM = getSupportFragmentManager();
        mFragmentLINESettings = (LINESettingsFragment) mFM.findFragmentById(R.id.fragment_line_settings);
    }

    /**
     * フラグメントを入れ替える(フラグメント管理用クラスに分けたい)
     * @param fragment R.id.main_screenに表示するフラグメント
     */
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = mFM.beginTransaction();
        transaction.replace(R.id.main_screen, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        mFM.executePendingTransactions();
    }
}
