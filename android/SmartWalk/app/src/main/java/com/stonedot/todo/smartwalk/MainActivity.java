package com.stonedot.todo.smartwalk;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        SpeechToTextListenerImpl.SpeechToTextListener,
        TextToSpeechProgressListener.TextToSpeechListener,
        LINEBroadcastReceiver.LINEBroadcastReceiverListener{

    private TextToSpeechManager mTTS;
    private SpeechToTextManager mSTT;

    private SmartWalkGuidance mGuidance;

    private FragmentManager mFM;
    private LINEFragment mLINEFragment;
    private LINEBroadcastReceiver mLINEReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // フラグメント関係
        mFM = getSupportFragmentManager();
        mLINEFragment = (LINEFragment) mFM.findFragmentById(R.id.fragment_line);

        // 音声関連のマネージャー
        mTTS = new TextToSpeechManager(this, this);
        mSTT = new SpeechToTextManager(this, this);

        // ガイダンス
        mGuidance = new SmartWalkGuidance(this, mTTS, mSTT);

        // 通知関係
        NotificationServiceAccess.showNotificationAccessSettingMenu(this);
        mLINEReceiver = new LINEBroadcastReceiver(this, this);

        speechTest();
    }

    private void speechTest() {
        Button mSoundRecognizeButton = (Button) findViewById(R.id.sound_recognize);
        mSoundRecognizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSTT.startSpeechToText(null);
            }
        });
    }

    private String lastText = "";
    @Override
    public void onLINENotification(String sender, String content) {
        // TODO 通知時のメッセージ形式
        String format = getString(R.string.format_line);
        String text = sender + format + content;

        // TODO Notification2回呼ばれるのなんとかならないか
        if(text.equals(lastText)) return;
        lastText = text;

        mGuidance.nextGuide(Guide.LINENotification, text);
        mLINEFragment.displayText(sender, content);
    }

    @Override
    public void onTextToSpeechFinished(Guide guide) {
        mGuidance.nextGuide(guide, null);
    }

    @Override
    public void onGetTextFromSpeech(String text, Guide guide) {
        mGuidance.nextGuide(guide, text);
    }

    @Override
    public void onGetTextFromSpeechFailed() {
        Toast.makeText(this, "音声入力なし", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTTS.shutdown();
        mSTT.shutdown();
    }
}
