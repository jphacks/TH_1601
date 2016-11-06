package com.stonedot.todo.smartwalk;

import android.util.Log;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.text.Normalizer.Form.NFKC;

/**
 * Created by komatsu on 2016/11/04.
 */

public class TextManager {

    public static String extractSpeakableChars(String text) {
        String normalizedText = Normalizer.normalize(text, NFKC);
        StringBuilder sb = new StringBuilder();
        Log.d("TextManager", text);
        for(int i = 0; i < text.length(); i++ ){
            //絵文字じゃなかったら追加
            if( Character.UnicodeBlock.of( text.charAt( i )) != Character.UnicodeBlock.MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS){
                sb.append( text.charAt(i) );
            }
        }
        return sb.toString();
    }
}
