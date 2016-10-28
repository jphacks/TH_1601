package com.stonedot.todo.smartwalk;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SmartWalkFragmentManager mFM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();

        NotificationServiceAccess.showNotificationAccessSettingMenu(this);
        mFM = new SmartWalkFragmentManager(this);
    }

    private void findView() {
        Button mSoundRecognizeButton = (Button) findViewById(R.id.sound_recognize);

        mSoundRecognizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "LINEに返信");
                    startActivityForResult(intent, 0);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "ActivityNotFoundException", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 0 || resultCode != RESULT_OK) return;
        ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        Toast.makeText(getApplicationContext(), results.get(0), Toast.LENGTH_LONG).show();

        super.onActivityResult(requestCode, resultCode, data);
    }
}
