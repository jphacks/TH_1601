package com.stonedot.todo.smartwalk;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by goto on 2016/10/29.
 */

public class HttpJSONClient extends AsyncTask<Void, Void, String> {

    public interface Responded {
        public void responded(String string);
    }

    private URL url;
    private String sendData;
    private Responded responceCallback;

    public HttpJSONClient(URL url, Map<String, String> data) {
        this.url = url;
        this.sendData = new JSONObject(data).toString();
    }

    public void post(Responded responceCallback) {
        this.responceCallback = responceCallback;
        execute();
    }

    @Override
    protected String doInBackground(Void... params) {
        StringBuilder sb = new StringBuilder();
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setInstanceFollowRedirects(false);
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.connect();
            try (OutputStream os = con.getOutputStream();
                 PrintStream ps = new PrintStream(os)) {
                ps.print(sendData);
            }
            try (InputStreamReader isr = new InputStreamReader(con.getInputStream());
                 BufferedReader br = new BufferedReader(isr)) {
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            }
            con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("HttpJSONClient", "Failed to send json data.");
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String str) {
        if(responceCallback != null) {
            responceCallback.responded(str);
        }
    }
}
