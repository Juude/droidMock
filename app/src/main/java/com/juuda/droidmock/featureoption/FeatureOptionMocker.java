package com.juuda.droidmock.featureoption;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.juuda.droidmock.mock.Mocker;

import java.lang.reflect.Field;

public class FeatureOptionMocker extends Mocker{

    private static final String TAG = "FeatureOptionMocker";

    public FeatureOptionMocker(Context context, Bundle extras) {
        super(context, extras);
    }

    @Override
    public void dump() {
        try {
            Class<?> class_FeatureOption = Class.forName("lewa.telephony.FeatureOption");
            for (Field field : class_FeatureOption.getDeclaredFields()) {
                field.setAccessible(true);
                Log.d(TAG, field.getName() + " : " + field.get(class_FeatureOption));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
