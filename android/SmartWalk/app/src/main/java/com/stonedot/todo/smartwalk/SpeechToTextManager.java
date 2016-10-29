package com.stonedot.todo.smartwalk;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

/**
 * Created by komatsu on 2016/10/29.
 */

public class SpeechToTextManager {

    private SpeechRecognizer mSTT;
    private SpeechToTextListenerImpl mSTTListener;

    public SpeechToTextManager(Context context, SpeechToTextListenerImpl.SpeechToTextListener listener) {
        mSTTListener = new SpeechToTextListenerImpl(context, listener);
        mSTT = SpeechRecognizer.createSpeechRecognizer(context);
        mSTT.setRecognitionListener(mSTTListener);
    }

    public void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSTT.startListening(intent);
    }

    public void stopSpeechToText() {
        mSTT.stopListening();
    }
}
