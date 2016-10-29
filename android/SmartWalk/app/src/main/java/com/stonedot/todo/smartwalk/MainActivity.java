package com.stonedot.todo.smartwalk;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements
        SpeechToTextListenerImpl.SpeechToTextListener,
        TextToSpeechProgressListener.TextToSpeechListener,
        LINEBroadcastReceiver.LINEBroadcastReceiverListener,
        SmartWalkGuidance.GuidanceListener,
        ReservationListFragment.ReservationListListener {

    private TextToSpeechManager mTTS;
    private SpeechToTextManager mSTT;

    private SmartWalkGuidance mGuidance;

    private FragmentManager mFM;
    private LINEFragment mLINEFragment;
    private ReservationListFragment mReservationListFragment;
    private LINEBroadcastReceiver mLINEReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // フラグメント関係
        mFM = getSupportFragmentManager();
        mLINEFragment = (LINEFragment) mFM.findFragmentById(R.id.fragment_line);
        // mReservationListFragment = (ReservationListFragment) mFM.findFragmentById(R.id.fragment_reservation_list);

        // 音声関連のマネージャー
        mTTS = new TextToSpeechManager(this, this);
        mSTT = new SpeechToTextManager(this, this);

        // ガイダンス
        mGuidance = new SmartWalkGuidance(this, this, mTTS, mSTT);

        // 通知関係
        NotificationServiceAccess.showNotificationAccessSettingMenu(this);
        mLINEReceiver = new LINEBroadcastReceiver(this, this);
    }

    private String lastText = "";
    @Override
    public void onLINENotification(String sender, String content) {
        String format = getString(R.string.format_line);
        String text = sender + format + content;

        // TODO Notification2回呼ばれるのなんとかならないか
        if(text.equals(lastText)) return;
        lastText = text;

        mGuidance.setLastReservation(new Reservation(SNS.LINE, sender, content, new Date()));
        mGuidance.nextGuide(Guide.LINENotification, text);
        mLINEFragment.displayText(sender, content);
    }

    @Override
    public void onTextToSpeechFinished(Guide guide) {
        mGuidance.nextGuide(guide, null);
    }

    @Override
    public void onGetTextFromSpeech(String text, Guide guide) {
        mGuidance.nextGuide(guide, text);
    }

    @Override
    public void onGetTextFromSpeechFailed(Guide guide) {
        mGuidance.nextGuide(guide, "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTTS.shutdown();
        mSTT.shutdown();
    }

    @Override
    public void onReserve(Reservation reservation) {
        mReservationListFragment.add(reservation);
    }

    @Override
    public void onItemClicked(Reservation reservation) {
        mGuidance.setLastReservation(reservation);
        mGuidance.nextGuide(Guide.DecideReply, "返信");
    }
}
