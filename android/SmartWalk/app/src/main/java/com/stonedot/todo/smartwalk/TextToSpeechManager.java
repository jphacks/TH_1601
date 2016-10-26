package com.stonedot.todo.smartwalk;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by komatsu on 2016/10/24.
 */

public class TextToSpeechManager implements TextToSpeech.OnInitListener {

    private Context mContext;
    private TextToSpeech mTTS;
    private boolean mInitCompletedFlag = false;

    public TextToSpeechManager(Context context) {
        mContext = context;
        mTTS = new TextToSpeech(context, this);
    }

    @Override
    // TODO エラー処理はもうちょっとよくならないものか
    public void onInit(int status) {
        if (status != TextToSpeech.SUCCESS) {
            Toast.makeText(mContext, "初期化エラー", Toast.LENGTH_SHORT).show();
            return;
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
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    // TODO どこでシャットダウンしようか…
    public void shutdown() {
        if(mTTS != null) mTTS.shutdown();
    }
}
