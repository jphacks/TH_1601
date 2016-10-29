package com.stonedot.todo.smartwalk;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LINESettingsFragment extends Fragment{

    private Activity mActivity;
    private View mFragment;
    private TextView mTextMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        mFragment = inflater.inflate(R.layout.fragment_line_settings, container, false);
        findViews();
        attachEvents();
        return mFragment;
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

    public void displayText(String sender, String content) {
        mTextMessage.setText(sender + "\n" + content);
    }
}
