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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

import jp.line.android.sdk.LineSdkContext;
import jp.line.android.sdk.LineSdkContextManager;
import jp.line.android.sdk.api.ApiClient;
import jp.line.android.sdk.api.ApiRequestFuture;
import jp.line.android.sdk.api.ApiRequestFutureListener;
import jp.line.android.sdk.login.LineAuthManager;
import jp.line.android.sdk.login.LineLoginFuture;
import jp.line.android.sdk.login.LineLoginFutureListener;
import jp.line.android.sdk.model.Profile;

public class LINEFragment extends Fragment {

    private Activity mActivity;
    private View mFragment;
    private TextToSpeechManager mTTS;

    private TextView mTextMessage;
    private Button mButtonLine;

    public void setTextToSpeechManager(TextToSpeechManager tts) {
        mTTS = tts;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        mFragment = inflater.inflate(R.layout.fragment_line_settings, container, false);
        findViews();
        attachEvents();
        notificationServiceStart();
        return mFragment;
    }

    /**
     * UIのインスタンスをメンバ変数として取得
     */
    private void findViews() {
        mTextMessage = (TextView) mFragment.findViewById(R.id.text_line_message);
        mButtonLine = (Button)mFragment.findViewById(R.id.buttonForLineLogin);
        mButtonLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LineSdkContext sdkContext = LineSdkContextManager.getSdkContext();
                LineAuthManager authManager = sdkContext.getAuthManager();
                LineLoginFuture loginFuture = authManager.login(getActivity());
                loginFuture.addFutureListener(loginComplete);
            }

        });
    }

    private LineLoginFutureListener loginComplete = new LineLoginFutureListener() {
        @Override
        public void loginComplete(LineLoginFuture future) {
            switch (future.getProgress()) {
                case SUCCESS:
                    Log.d("LineFragment", "Line login succeeded.");
                    LineSdkContext sdkContext = LineSdkContextManager.getSdkContext();
                    ApiClient apiClient = sdkContext.getApiClient();
                    apiClient.getMyProfile(myProfileComplete);
                    break;
                case CANCELED:
                    Log.d("LineFragment", "Line login canceled.");
                    break;
                default:
                    Log.d("LineFragment", "Line login go something wrong.");
                    break;
            }
        }
    };

    private static final String FILENAME_LINE_ID = "line_id";
    private ApiRequestFutureListener myProfileComplete = new ApiRequestFutureListener<Profile>() {
        @Override
        public void requestComplete(ApiRequestFuture<Profile> future) {
            switch (future.getStatus()) {
                case SUCCESS:
                    Log.d("LineFragment", "Profile retrieving succeeded.");
                    Profile profile = future.getResponseObject();
                    try(FileOutputStream fos = getActivity().openFileOutput(FILENAME_LINE_ID, Context.MODE_PRIVATE)) {
                        fos.write(profile.mid.getBytes());
                        Log.d("LineFragment", "Line ID: " + profile.mid.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("LineFragment", "Saving LINE ID failed.");
                    }
                    break;
                default:
                    Log.d("LineFragment", "Profile retrieving failed.");
                    break;
            }
        }
    };

    /**
     * ボタンが押されたときにhoge()を実行するなど、UIイベントと関数を結びつける
     */
    private void attachEvents() {
    }

    /**
     * NotificatonListenerServiceの開始処理
     */
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
            mTextMessage.setText(sender + "\n" + content);
            speech(sender, content);
            // reply(sender);
        }
    };

    // TODO 通知時のメッセージ形式
    private void speech(String sender, String content) {
        if(mTTS == null) return;
        String format = mActivity.getString(R.string.format_line);
        String text = sender + format + content;
        mTTS.speechText(text);
    }

    // TODO よく考えたら自動返信とかめっちゃ危険じゃん！
    private void reply(String sender) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("line://msg/text/ありがとう"));
        try {
            mActivity.startActivityForResult(intent, 0);
        } catch (Exception e) {
            Toast.makeText(mActivity, "LINEアプリをインストールしてください", Toast.LENGTH_SHORT).show();
        }
    }


}
