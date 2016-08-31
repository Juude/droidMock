package com.juude.droidmocks;


/**
 * Created by sjd on 16/8/31.
 */
public class Preconditions {
    public static<T> T checkNotNull(T object) {
        if (object == null) {
            throw new RuntimeException("check Null object ");
        } else {
            return object;
        }
    }
}
