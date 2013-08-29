
package com.lewa.droidtest.test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TestUtils {
    @Retention(RetentionPolicy.RUNTIME)
    static public @interface TestMe {
    }

}
