package com.stonedot.todo.smartwalk;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class LINERegisterActivity extends Activity {

    private static final String endpoint = "https://smartwalk.stonedot.com/register/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_register);

        // ユーザー登録関係
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            String token = uri.getPath().substring(1);
            Log.d("Register", token);

            String mid = "";
            try(
                    FileInputStream fis = openFileInput(LINEFragment.FILENAME_LINE_ID);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr)
            ) {
                mid = br.readLine();
                Log.d("LineFragment", "Line ID: " + mid);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("LineFragment", "Loading LINE ID failed.");
            }

            try {
                URL url = new URL(endpoint);
                HashMap<String, String> map = new HashMap<>();
                map.put("token", token);
                map.put("mid", mid);
                HttpJSONClient client = new HttpJSONClient(url, map);
                client.post(new HttpJSONClient.Responded() {
                    @Override
                    public void responded(String string) {
                        Log.d("LINERegisterAcitivity", "Response");
                        Log.d("LINERegisterAcitivity", string);
                        Toast toast = Toast.makeText(getApplicationContext(), "LINEの登録が完了しました。", Toast.LENGTH_LONG);
                        toast.show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(intent);
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("LINERegisterAcitivity", "Bad URL.");
            }
        }
    }
}
