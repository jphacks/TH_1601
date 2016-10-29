package com.stonedot.todo.smartwalk;

import android.app.Activity;
import android.util.Log;

import static com.stonedot.todo.smartwalk.Guide.ConfirmReply;
import static com.stonedot.todo.smartwalk.Guide.ConfirmSend;
import static com.stonedot.todo.smartwalk.Guide.DecideReply;
import static com.stonedot.todo.smartwalk.Guide.Finish;
import static com.stonedot.todo.smartwalk.Guide.GetAnswerConfirmReply;
import static com.stonedot.todo.smartwalk.Guide.GetAnswerConfirmSend;
import static com.stonedot.todo.smartwalk.Guide.RepeatReply;
import static com.stonedot.todo.smartwalk.Guide.Reserve;
import static com.stonedot.todo.smartwalk.Guide.Send;
import static com.stonedot.todo.smartwalk.Guide.StartReply;

/**
 * Created by komatsu on 2016/10/29.
 */

public class SmartWalkGuidance {

    private Activity mActivity;
    private TextToSpeechManager mTTS;
    private SpeechToTextManager mSTT;

    private String lastSender = "";
    private String lastContent = "";

    public SmartWalkGuidance(Activity activity, TextToSpeechManager tts, SpeechToTextManager sst) {
        mActivity = activity;
        mTTS = tts;
        mSTT = sst;
    }

    public void setLastSender(String name) {
        lastSender = name;
    }

    public void setLastContent(String content) {
        lastContent = content;
    }

    private void reserve() {
        // TODO 保留措置
    }

    // TODO メッセージの割り込み対策
    public void nextGuide(Guide guide, String text) {
        if(guide == null) return;
        Log.d("nextGuide", guide.toString());
        switch (guide) {
            case LINENotification:
                mTTS.textToSpeech(text, ConfirmReply);
                break;

            case ConfirmReply:
                mTTS.textToSpeech("「返信」と言うと返信します。", GetAnswerConfirmReply);
                break;

            case GetAnswerConfirmReply:
                mSTT.speechToText(DecideReply);
                break;

            case DecideReply:
                if(!text.equals("返信")) {
                    mTTS.textToSpeech("保留されます。", Reserve);
                    break;
                }
                mTTS.textToSpeech("メッセージを入力してください。", StartReply);
                break;

            case StartReply:
                mSTT.speechToText(RepeatReply);
                break;

            case RepeatReply:
                if(text == null || text == "") {
                    mTTS.textToSpeech("メッセージの取得に失敗しました。", ConfirmReply);
                    break;
                }
                String repeatMessage = "返信メッセージは、" + text + "、です。";
                mTTS.textToSpeech(repeatMessage, ConfirmSend);
                break;

            case ConfirmSend:
                mTTS.textToSpeech("「送信」と言うと送信します。", GetAnswerConfirmSend);
                break;

            case GetAnswerConfirmSend:
                mSTT.speechToText(Send);
                break;

            case Send:
                if(!text.equals("送信")) {
                    mTTS.textToSpeech("メッセージを再入力してください。", StartReply);
                    break;
                }
                // TODO メッセージ送信
                if(false) break;
                mTTS.textToSpeech("メッセージを送信しました。", Finish);
                break;

            case Finish: break;

            case Reserve:
                reserve();
                break;

            case Failed:
                mTTS.textToSpeech("音声認識に失敗しました。", Finish);
                break;

            default: break;
        }
    }
}
