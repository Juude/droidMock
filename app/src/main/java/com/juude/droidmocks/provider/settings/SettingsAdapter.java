
package com.juude.droidmocks.provider.settings;

import android.content.ContentResolver;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.Method;

public class SettingsAdapter {
    private static final boolean DEBUG = true;
    private static final String TAG = "SettingsAdapter";

    public static void putInt(ContentResolver resolver, String keyName, int value) {
        String fieldClassName;
        if (Build.VERSION.SDK_INT < 17) {
            fieldClassName = "android.provider.Settings$Secure";
        }
        else {
            fieldClassName = "android.provider.Settings$Global";
        }
        try {
            Class<?> settingsClass = Class.forName("android.provider.Settings$System");
            Class<?> fieldClas = Class.forName(fieldClassName);
            String name = (String) fieldClas.getField(keyName).get(fieldClas);
            Method method = settingsClass
                    .getMethod("putInt", ContentResolver.class, String.class, int.class);
            method.invoke(settingsClass, resolver, name, value);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getInt(ContentResolver resolver, String keyName, int defValue) {
        String fieldClassName;
        String SettingsClassName;

        int result = defValue;
        if (Build.VERSION.SDK_INT < 17) {
            SettingsClassName = "android.provider.Settings$System";
            fieldClassName = "android.provider.Settings$Secure";
        }
        else {
            SettingsClassName = "android.provider.Settings$Global";
            fieldClassName = "android.provider.Settings$Global";
        }
        try {
            Class<?> settingsClass = Class.forName(SettingsClassName);
            Class<?> fieldClass = Class.forName(fieldClassName);
            String name = (String) fieldClass.getField(keyName).get(fieldClassName);
            Method method = settingsClass.getMethod("getInt", ContentResolver.class, String.class,
                    int.class);
            if (DEBUG) {
                Log.e(TAG, "name : " + name);
            }
            if (DEBUG) {
                Log.e(TAG, "method : " + method);
            }
            result = (Integer) method.invoke(settingsClass, resolver, name, defValue);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
