package com.stonedot.todo.smartwalk;

import android.content.Context;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by komatsu on 2016/10/29.
 */

public class SpeechToTextListenerImpl implements RecognitionListener {

    public interface SpeechToTextListener {
        void onGetTextFromSpeech(String text, Guide guide);
        void onGetTextFromSpeechFailed();
    }
    private SpeechToTextListener mListener;

    private Context mContext;

    private Guide nextGuide = Guide.Guide;

    public SpeechToTextListenerImpl(Context context, SpeechToTextListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {
        Toast.makeText(mContext, "音声入力開始", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBeginningOfSpeech() {
        // Toast.makeText(mContext, "音声が入力されました", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {
        Toast.makeText(mContext, "音声入力終了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(int error) {
        switch (error) {
            case SpeechRecognizer.ERROR_AUDIO:
                Toast.makeText(mContext, "音声データを認識できませんでした", Toast.LENGTH_SHORT).show();
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                Toast.makeText(mContext, "未確認のエラーが発生しました", Toast.LENGTH_SHORT).show();
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                Toast.makeText(mContext, "音声認識機能を使用する権限がありません", Toast.LENGTH_SHORT).show();
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                // ネットワークエラー(その他)
                Toast.makeText(mContext, "ネットワークエラーが発生しました", Toast.LENGTH_SHORT).show();
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                // ネットワークタイムアウトエラー
                Toast.makeText(mContext, "ネットワークとの接続がタイムアウトしました", Toast.LENGTH_SHORT).show();
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                // 音声認識結果無し
                Toast.makeText(mContext, "音声を認識できませんでした。", Toast.LENGTH_SHORT).show();
                if(mListener != null) mListener.onGetTextFromSpeechFailed();
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                Toast.makeText(mContext, "音声認識プログラムが使用中です", Toast.LENGTH_SHORT).show();
                break;
            case SpeechRecognizer.ERROR_SERVER:
                Toast.makeText(mContext, "サーバのエラーです", Toast.LENGTH_SHORT).show();
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                // 音声入力無し
                Toast.makeText(mContext, "音声入力がありませんでした。", Toast.LENGTH_SHORT).show();
                if(mListener != null) mListener.onGetTextFromSpeechFailed();
                break;
            default:
                break;
        }
    }

    // Bundleが新しくなってしまったもので、Guideを受け取れないので、
    // ListenerImplだが特例でsetメソッド設置
    public void setNextGuide(Guide guide) {
        nextGuide = guide;
    }

    @Override
    public void onResults(Bundle bundle) {
        // TODO Resultから一番ちゃんとしたものを選ぶ方法はある？
        ArrayList<String> results = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if(mListener != null) mListener.onGetTextFromSpeech(results.get(0), nextGuide);
    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }
}
