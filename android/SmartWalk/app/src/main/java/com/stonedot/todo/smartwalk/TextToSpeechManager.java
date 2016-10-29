package com.stonedot.todo.smartwalk;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by komatsu on 2016/10/24.
 */

public class TextToSpeechManager implements TextToSpeech.OnInitListener {

    private Context mContext;
    private TextToSpeechProgressListener mTTSListener;
    private TextToSpeech mTTS;
    private boolean mInitCompletedFlag = false;

    public TextToSpeechManager(Context context, TextToSpeechProgressListener.TextToSpeechListener ttsListener) {
        mContext = context;
        mTTSListener = new TextToSpeechProgressListener(ttsListener);
        mTTS = new TextToSpeech(context, this);
    }

    @Override
    public void onInit(int status) {
        if (status != TextToSpeech.SUCCESS) {
            Toast.makeText(mContext, "初期化エラー", Toast.LENGTH_SHORT).show();
            return;
        }

        if(mTTSListener != null) {
            mTTS.setOnUtteranceProgressListener(mTTSListener);
        }

        Locale locale = Locale.JAPANESE;
        if (mTTS.isLanguageAvailable(locale) >= TextToSpeech.LANG_AVAILABLE) {
            mTTS.setLanguage(locale);
            mInitCompletedFlag = true;
        }
        else Toast.makeText(mContext, "言語選択エラー", Toast.LENGTH_SHORT).show();
    }

    public void speechText(String text, Guide guide) {
        if (mTTS == null || text.length() <= 0 || !mInitCompletedFlag) return;
        HashMap<String, String> myHashAlarm = new HashMap<String, String>();
        myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, guide.toString());
        mTTS.speak(removePictureChars(text), TextToSpeech.QUEUE_ADD, myHashAlarm);
    }

    public void shutdown() {
        if(mTTS == null) return;
        mTTS.shutdown();
        mTTS = null;
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
