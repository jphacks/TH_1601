package com.stonedot.todo.smartwalk;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by goto on 2016/10/30.
 */

public class UserDataStorage {

    public static final String FILENAME_LINE_ID = "line_id";
    public static String getLineMid(Context context) {
        String mid = "";
        try(
                FileInputStream fis = context.openFileInput(FILENAME_LINE_ID);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr)
        ) {
            mid = br.readLine();
            Log.d("LineFragment", "Line ID: " + mid);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("LineFragment", "Loading LINE ID failed.");
        }
        return mid;
    }

    public static void putLineMid(Context context, String mid) {
        try(FileOutputStream fos = context.openFileOutput(FILENAME_LINE_ID, Context.MODE_PRIVATE)) {
            fos.write(mid.getBytes());
            Log.d("LineFragment", "Line ID: " + mid);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("LineFragment", "Saving LINE ID failed.");
        }
    }
}
