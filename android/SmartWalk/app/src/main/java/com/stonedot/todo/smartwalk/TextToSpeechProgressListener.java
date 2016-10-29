package com.stonedot.todo.smartwalk;

import android.speech.tts.UtteranceProgressListener;

/**
 * Created by komatsu on 2016/10/29.
 * こいつは実装じゃなくて継承なので注意！
 */

public class TextToSpeechProgressListener extends UtteranceProgressListener {

    public interface TextToSpeechListener {
        void onTextToSpeechFinished(Guide guide);
    }
    private TextToSpeechListener mListener;

    public TextToSpeechProgressListener(TextToSpeechListener listener) {
        mListener = listener;
    }

    @Override
    public void onStart(String s) {

    }

    @Override
    public void onDone(String s) {
        Guide guide = Guide.valueOf(s);
        mListener.onTextToSpeechFinished(guide);
    }

    @Override
    public void onError(String s) {

    }
}