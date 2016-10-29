package com.stonedot.todo.smartwalk;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by komatsu on 2016/10/29.
 */

public class SmartWalkGuidance {

    private Activity mActivity;
    private TextToSpeechManager mTTS;
    private SpeechToTextManager mSTT;

    public SmartWalkGuidance(Activity activity, TextToSpeechManager tts, SpeechToTextManager sst) {
        mActivity = activity;
        mTTS = tts;
        mSTT = sst;
    }

    public void nextGuide(Guide currentGuide, String text) {
        switch (currentGuide) {
            case LINENotification:
                mTTS.speechText("今すぐ返信しますか？", Guide.ConfirmReply);
                break;

            case ConfirmReply:
                if(text != "はい") break;
                // TODO なんか鳴らす
                Toast.makeText(mActivity, "メッセージを話してください", Toast.LENGTH_SHORT).show();
                mSTT.startSpeechToText(Guide.StartReply);
                break;

            case StartReply:


            default: break;
        }
    }
}
