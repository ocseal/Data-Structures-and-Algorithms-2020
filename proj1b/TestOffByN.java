import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    @Test
    public void testOffBy5() {
        OffByN ob5 = new OffByN(5);
        assertTrue(ob5.equalChars('a', 'f'));
        assertFalse(ob5.equalChars('f', 'h'));
    }
}

