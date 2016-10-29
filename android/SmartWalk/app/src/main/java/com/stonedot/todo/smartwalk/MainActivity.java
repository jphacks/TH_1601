package com.stonedot.todo.smartwalk;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import jp.line.android.sdk.LineSdkContextManager;

public class MainActivity extends AppCompatActivity implements
        SpeechRecognitionListenerImpl.SpeechListener,
        TextToSpeechFinishListenerImpl.SpeechFinishListener {

    private SpeechRecognitionListenerImpl recognitionListener;
    private SmartWalkFragmentManager mFM;
    private SpeechRecognizer mSR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotificationServiceAccess.showNotificationAccessSettingMenu(this);
        mFM = new SmartWalkFragmentManager(this, this);

        recognitionListener = new SpeechRecognitionListenerImpl(this, this);
        mSR = SpeechRecognizer.createSpeechRecognizer(this);
        mSR.setRecognitionListener(recognitionListener);

        // LineSDK を初期化
        LineSdkContextManager.initialize(this);

        speechTest();
    }

    private void speechTest() {
        Button mSoundRecognizeButton = (Button) findViewById(R.id.sound_recognize);

        mSoundRecognizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSpeechRecognition();
            }
        });
    }

    private void startSpeechRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSR.startListening(intent);
    }

    @Override
    public void onTextToSpeechFinished(String text) {
        // TODO 音声出力完了
    }

    @Override
    public void onGetSpeechToText(String text) {
        // TODO 音声入力完了
    }

    @Override
    public void onGetSpeechToTextFailed() {
        // TODO 音声入力なし
    }
}
