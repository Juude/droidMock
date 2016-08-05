
package com.juude.droidmocks.mock;

import android.os.Bundle;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MockUtils {
    @Retention(RetentionPolicy.RUNTIME)
    static public @interface Mock {
    }
    
    public static String getString(Bundle bundle, String key, String def) {
        String result = bundle.getString(key);
        result = result == null ? def : result;
        return result;
    }
    
    public static int getInt(Bundle bundle, String key, int def) {
        String str = bundle.getString(key);
        int result = def;
        try {
            result = Integer.parseInt(str);
        }
        catch(Exception e) {
        }
        return result; 
    }
}
