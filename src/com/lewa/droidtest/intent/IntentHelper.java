package com.lewa.droidtest.intent;

import android.content.Intent;

public class IntentHelper {
    public static String dump(Intent i) {
        StringBuilder builder = new StringBuilder();
        builder.append(i.toString());
        if(i.getExtras() != null) {
            for(String key : i.getExtras().keySet()) {
                builder.append("[")
                       .append(key)
                       .append(" , ")
                       .append(i.getExtra(key))
                       .append("]");
            }
        }
        return builder.toString();
    }
}
