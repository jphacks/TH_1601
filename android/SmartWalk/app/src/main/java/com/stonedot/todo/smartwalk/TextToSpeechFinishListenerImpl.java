package com.stonedot.todo.smartwalk;

import android.speech.tts.UtteranceProgressListener;

/**
 * Created by komatsu on 2016/10/29.
 */

public class TextToSpeechFinishListenerImpl extends UtteranceProgressListener {

    public interface SpeechFinishListener {
        void onTextToSpeechFinished(String text);
    }
    private SpeechFinishListener mListener;

    public TextToSpeechFinishListenerImpl(SpeechFinishListener listener) {
        mListener = listener;
    }

    @Override
    public void onStart(String s) {

    }

    @Override
    public void onDone(String s) {
        // TODO 音声出力完了時の処理
        mListener.onTextToSpeechFinished(s);
    }

    @Override
    public void onError(String s) {

    }
}
