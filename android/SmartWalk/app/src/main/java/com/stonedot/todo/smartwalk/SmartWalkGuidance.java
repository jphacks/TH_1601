package com.stonedot.todo.smartwalk;

import android.app.Activity;
import android.util.Log;

import static com.stonedot.todo.smartwalk.Guide.Guide;
import static com.stonedot.todo.smartwalk.Guide.Send;

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

    // TODO メッセージの割り込み対策
    public void nextGuide(Guide guide, String text) {
        if(guide == null) return;
        Log.d("nextGuide", guide.toString());
        switch (guide) {
            case LINENotification:
                Log.d("LINENotification", text);
                mTTS.speechText(text, Guide.ConfirmReply);
                break;

            case ConfirmReply:
                Log.d("ConfirmReply", "");
                mTTS.speechText("今すぐ返信しますか? 返信するには、「はい」と言ってください。", Guide.GetAnswerConfirmReply);
                break;

            case GetAnswerConfirmReply:
                mSTT.startSpeechToText(Guide.DecideReply);
                break;

            case DecideReply:
                Log.d("DecideReply", text);
                if(!text.equals("はい")) break; // TODO 返信拒否
                mTTS.speechText("メッセージを入力してください。", Guide.StartReply);
                break;

            case StartReply:
                mSTT.startSpeechToText(Guide.RepeatReply);
                break;

            case RepeatReply:
                Log.d("RepeatReply", text);
                if(text == null || text == "") break; // TODO 返信メッセージ取得失敗
                String repeatMessage = "返信メッセージは、" + text + "、です。";
                mTTS.speechText(repeatMessage, Guide.ConfirmSend);
                break;

            case ConfirmSend:
                mTTS.speechText("返信しますか？", Guide.GetAnswerConfirmSend);
                break;

            case GetAnswerConfirmSend:
                mSTT.startSpeechToText(Send);
                break;

            case Send:
                Log.d("Send", text);
                if(!text.equals("はい")) break; // TODO 送信拒否、ConfirmReplyへ
                // TODO メッセージ送信
                mTTS.speechText("メッセージを送信しました", Guide.Finish);
                break;

            case Finish:
                break;

            default: break;
        }
    }
}
