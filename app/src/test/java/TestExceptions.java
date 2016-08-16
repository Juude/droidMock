import org.junit.Test;

/**
 * Created by sjd on 16/8/16.
 */
public class TestExceptions {

    @Test
    public void testCatchRuntimeExceptions() {
        try {
            Integer.parseInt("333f");
        } catch (Exception e) {
            System.out.println("e : " + e);
        }
    }

    @Test
    public void testNotCatchExceptions() {
        Integer.parseInt("333f");
    }
}
