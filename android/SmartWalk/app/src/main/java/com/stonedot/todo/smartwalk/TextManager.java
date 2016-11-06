package com.stonedot.todo.smartwalk;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by komatsu on 2016/11/04.
 */

public class TextManager {

    public static final String MATCH_EISUU = "a-zA-Z0-9";
    public static final String MATCH_HIRAGANA = "\u3041-\u3096";
    public static final String MATCH_KATAKANA = "\u30A1-\u30FA";
    public static final String MATCH_KANJI = "一-龠";

    public static String extractSpeakableChars(String text) {
        Pattern p = Pattern.compile(allowRegex());
        Matcher m = p.matcher(text);
        StringBuilder builder = new StringBuilder();
        while (m.find())  {
            if (BuildConfig.DEBUG) Log.d("TextManager", m.group());
            builder.append(m.group());
        }
        return builder.toString();
    }

    private static String allowRegex() {
        return toMatchRegex(
                MATCH_EISUU,
                MATCH_HIRAGANA,
                MATCH_KATAKANA
        );
    }

    private static String toMatchRegex(String... codes) {
        StringBuilder builder = new StringBuilder();
        for(String code : codes) builder.append(code);
        return "[" + builder.toString() + "]+";
    }
}
