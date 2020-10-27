import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by hug.
 */
public class TestRedBlackFloorSet {
    @Test
    public void randomizedTest() {
        int i = 0;
        int j = 0;
        AListFloorSet a = new AListFloorSet();
        RedBlackFloorSet rb = new RedBlackFloorSet();
        while (i < 1000000) {
            int z = StdRandom.uniform(-5000, 5000);
            a.add(z);
            rb.add(z);
            i++;
        }

        while (j < 100000) {
            int z = StdRandom.uniform(-5000, 5000);
            assertEquals(a.floor(z), rb.floor(z), 0.000001);
            j++;
        }
    }
}
