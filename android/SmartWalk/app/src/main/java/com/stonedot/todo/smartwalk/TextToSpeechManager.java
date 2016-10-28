package com.stonedot.todo.smartwalk;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by komatsu on 2016/10/24.
 */

public class TextToSpeechManager implements TextToSpeech.OnInitListener {

    private Context mContext;
    private UtteranceProgressListener mFinishListener;
    private TextToSpeech mTTS;
    private boolean mInitCompletedFlag = false;

    public TextToSpeechManager(Context context, UtteranceProgressListener finishListener) {
        mContext = context;
        mFinishListener = finishListener;
        mTTS = new TextToSpeech(context, this);
    }

    @Override
    // TODO エラー処理はもうちょっとよくならないものか
    public void onInit(int status) {
        if (status != TextToSpeech.SUCCESS) {
            Toast.makeText(mContext, "初期化エラー", Toast.LENGTH_SHORT).show();
            return;
        }

        if(mFinishListener != null) {
            mTTS.setOnUtteranceProgressListener(mFinishListener);
        }

        Locale locale = Locale.JAPANESE;
        if (mTTS.isLanguageAvailable(locale) >= TextToSpeech.LANG_AVAILABLE) {
            mTTS.setLanguage(locale);
            mInitCompletedFlag = true;
        }
        else Toast.makeText(mContext, "言語選択エラー", Toast.LENGTH_SHORT).show();
    }

    public void speechText(String text) {
        if (mTTS == null || text.length() <= 0 || !mInitCompletedFlag) return;
        if (mTTS.isSpeaking()) mTTS.stop();

        // TODO 連続でメッセージが来た時の対処(キューに突っ込む)
        mTTS.speak(removePictureChars(text), TextToSpeech.QUEUE_FLUSH, null);
    }

    // TODO どこでシャットダウンしようか…
    public void shutdown() {
        if(mTTS != null) mTTS.shutdown();
    }

    // TODO LINEで動かないぞ
    private String removePictureChars(String text) {
        StringBuffer buffer = new StringBuffer();
        for(char c : text.toCharArray()) {
            if(Character.UnicodeBlock.of(c) == Character.UnicodeBlock.PRIVATE_USE_AREA) continue;
            buffer.append(c);
        }
        return buffer.toString();
    }
}
