package com.juuda.droidmock.intent;

import android.content.Intent;

public class IntentHelper {
    @SuppressWarnings("deprecation")
    public static String dump(Intent i) {
        StringBuilder builder = new StringBuilder();
        builder.append(i.toString());
        if(i.getExtras() != null) {
            for(String key : i.getExtras().keySet()) {
                builder.append("[")
                       .append(key)
                       .append(" , ")
                       .append(i.getStringExtra(key))
                       .append("]");
            }
        }
        return builder.toString();
    }
}
