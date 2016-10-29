package com.stonedot.todo.smartwalk;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

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
        Log.d("nextGuide", guide.toString());
        switch (guide) {
            case LINENotification:
                Log.d("LINENotification", text);
                mTTS.speechText(text, Guide.ConfirmReply);
                break;

            case ConfirmReply:
                Log.d("ConfirmReply", "");
                mTTS.speechText("今すぐ返信しますか?", Guide.GetAnswerConfirmReply);
                break;

            case GetAnswerConfirmReply:
                Toast.makeText(mActivity, "返信するには、「はい」と言ってください。", Toast.LENGTH_SHORT).show();
                mSTT.startSpeechToText(Guide.StartReply);
                break;

            case StartReply:
                Log.d("ConfirmReply", text);
                if(text != "はい") break; // TODO 返信拒否
                // TODO なんか鳴らす
                Toast.makeText(mActivity, "メッセージを話してください", Toast.LENGTH_SHORT).show();
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
                if(text != "はい") break; // TODO 送信拒否、ConfirmReplyへ
                Toast.makeText(mActivity, "送信：" + text, Toast.LENGTH_SHORT).show();
                // TODO メッセージ送信
                mTTS.speechText("メッセージを送信しました", Guide.Finish);
                break;

            case Finish:
                break;

            default: break;
        }
    }
}
