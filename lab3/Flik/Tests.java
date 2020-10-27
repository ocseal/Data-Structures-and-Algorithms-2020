import static org.junit.Assert.*;
import org.junit.Test;

public class Tests {
    @Test
    public void testNumbers() {
        Flik.isSameNumber(128, 128);
        assertTrue(Flik.isSameNumber(11, 11));
        assertTrue(Flik.isSameNumber(100, 100));
        assertFalse(Flik.isSameNumber(127, 128));
        assertTrue(Flik.isSameNumber(127, 127));
        System.out.println(Flik.isSameNumber(127, 127));
        System.out.println(Flik.isSameNumber(128, 128));
    }

    /* Apparently there's an issue with the Java Integer class.
    In order to save memory, we can't use == for integers
    in the range of -128 to +127. We have to use .equals() instead.
     */

}
