package com.stonedot.todo.smartwalk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class LINEFriendRegisterActivity extends AppCompatActivity {

    private static final String endpoint = "https://smartwalk.stonedot.com/users/friend";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_friend_register);

        // フレンド登録関係
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            String token = uri.getPath().substring(1);
            Log.d("Friend", token);

            String mid = UserDataStorage.getLineMid(this);

            try {
                URL url = new URL(endpoint);
                HashMap<String, String> map = new HashMap<>();
                map.put("friend_token", token);
                map.put("mid", mid);
                HttpJSONClient client = new HttpJSONClient(url, map);
                client.post(new HttpJSONClient.Responded() {
                    @Override
                    public void responded(int code, String message, String content) {
                        Log.d("LINERegisterAcitivity", "Code: " + code);
                        Log.d("LINERegisterAcitivity", "Message: " + message);
                        Log.d("LINERegisterAcitivity", "Response");
                        Log.d("LINERegisterAcitivity", content);
                        if(code == 200) {
                            Toast toast = Toast.makeText(getApplicationContext(), "友達登録が完了しました。", Toast.LENGTH_LONG);
                            toast.show();
                        } else if (code == 204) {
                            Toast toast = Toast.makeText(getApplicationContext(), "友達登録の必要がありません。", Toast.LENGTH_LONG);
                            toast.show();
                        }  else {
                            Toast toast = Toast.makeText(getApplicationContext(), "友達登録に失敗しました。", Toast.LENGTH_LONG);
                            toast.show();
                        }
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
