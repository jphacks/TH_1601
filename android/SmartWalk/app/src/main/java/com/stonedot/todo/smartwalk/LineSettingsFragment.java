package com.stonedot.todo.smartwalk;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LINESettingsFragment extends Fragment {

    public interface LINESettingsListener {
        // TODO: Update argument type and name
        // void onFragmentInteraction(Uri uri);
    }
    private LINESettingsListener mListener;

    private Activity mActivity;
    private View mFragment;

    private TextView mTextMessage;

    private LINENotificationService mLINEService;

    public void LINESettingsFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LINESettingsListener) mListener = (LINESettingsListener) context;
        else throw new RuntimeException(context.toString() + " must implement LINESettingsListener");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        mFragment = inflater.inflate(R.layout.fragment_line_settings, container, false);
        findViews();
        attachEvents();
        init();
        return mFragment;
    }

    /**
     * フラグメントが生成されたときに実行される初期化関数
     */
    private void init() {
        Intent intent = new Intent(mActivity, LINENotificationService.class);
        mActivity.startService(intent);
        LocalBroadcastManager.getInstance(mActivity).registerReceiver(onNotice, new IntentFilter("Msg"));
    }

    /**
     * UIのインスタンスをメンバ変数として取得
     */
    private void findViews() {
        mTextMessage = (TextView) mFragment.findViewById(R.id.text_line_message);
    }

    /**
     * ボタンが押されたときにhoge()を実行するなど、UIイベントと関数を結びつける
     */
    private void attachEvents() {
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            // mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private BroadcastReceiver onNotice= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String pack = intent.getStringExtra("package");
            String title = intent.getStringExtra("title");
            String text = intent.getStringExtra("text");
            mTextMessage.setText(text);
        }
    };
}
