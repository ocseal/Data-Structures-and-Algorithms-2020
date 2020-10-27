package es.datastructur.synthesizer;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Ethan Wu
 */

public class TestArrayRingBuffer {
    @Test
    public void testBasicInOrder() {
        BoundedQueue<Integer> q = new ArrayRingBuffer<>(16);
        for (int i = 0; i < q.capacity(); i++) {
            q.enqueue(i);
        }
        for (int i = 0; i < q.capacity(); i++) {
            assertEquals(i, (int) q.dequeue());
        }
    }

    @Test
    public void testBasicInterleaved() {
        BoundedQueue<Integer> q = new ArrayRingBuffer<>(16);
        for (int i = 0; i < q.capacity() * 4; i++) {
            q.enqueue(i);
            assertEquals(i, (int) q.dequeue());
        }
    }

    @Test
    public void testThrowsOnOverflow() {
        BoundedQueue<Integer> q = new ArrayRingBuffer<>(1);
        q.enqueue(1);
        try {
            q.enqueue(2);
            fail("Expected q.enqueue() to throw RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Ring buffer overflow", e.getMessage());
        }
    }

    @Test
    public void testThrowsOnDequeueUnderflow() {
        BoundedQueue<Integer> q = new ArrayRingBuffer<>(1);
        q.enqueue(1);
        q.dequeue();
        try {
            q.dequeue();
            fail("Expected q.dequeue() to throw RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Ring buffer underflow", e.getMessage());
        }
    }

    @Test
    public void testThrowsOnPeekUnderflow() {
        BoundedQueue<Integer> q = new ArrayRingBuffer<>(1);
        try {
            q.peek();
            fail("Expected q.peek() to throw RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Ring buffer underflow", e.getMessage());
        }
    }

    @Test
    public void testBasicIterator() {
        BoundedQueue<Integer> q = new ArrayRingBuffer<>(16);
        for (int i = 0; i < q.capacity(); i++) {
            q.enqueue(i);
        }
        Iterator<Integer> it = q.iterator();
        for (int i = 0; i < q.capacity(); i++) {
            assertTrue(it.hasNext());
            assertEquals(i, (int) it.next());
        }
        assertFalse(it.hasNext());
    }

    @Test
    public void testIteratorOffset() {
        BoundedQueue<Integer> q = new ArrayRingBuffer<>(16);
        for (int i = 0; i < q.capacity() / 3; i++) {
            q.enqueue(i);
            q.dequeue();
        }
        for (int i = 0; i < q.capacity(); i++) {
            q.enqueue(i);
        }
        Iterator<Integer> it = q.iterator();
        for (int i = 0; i < q.capacity(); i++) {
            assertTrue(it.hasNext());
            assertEquals(i, (int) it.next());
        }
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Expected it.next() to throw NoSuchElementException");
        } catch (NoSuchElementException e) {
            // ignore
        }
    }

    @Test
    public void testTrivialEquals() {
        BoundedQueue<Integer> a1 = new ArrayRingBuffer<>(16);
        BoundedQueue<Integer> a2 = new ArrayRingBuffer<>(16);
        BoundedQueue<Integer> b1 = new ArrayRingBuffer<>(8);
        assertEquals(a1, a1);
        assertEquals(a1, a2);
        assertNotEquals(a1, b1);
        for (int i = 0; i < a1.capacity(); i++) {
            a1.enqueue(i);
            assertNotEquals(a1, a2);
            assertNotEquals(a2, a1);
            a2.enqueue(i);
            assertEquals(a1, a2);
        }
        a2.enqueue(a2.dequeue());
        assertNotEquals(a1, a2);
    }

    @Test
    public void testOffsetEquals() {
        BoundedQueue<Integer> a1 = new ArrayRingBuffer<>(16);
        BoundedQueue<Integer> a2 = new ArrayRingBuffer<>(16);

        for (int i = 0; i < a1.capacity() / 2; i++) {
            a1.enqueue(i);
            assertNotEquals(a1, a2);
            assertNotEquals(a2, a1);
            a1.dequeue();
            assertEquals(a1, a2);
        }

        for (int i = 0; i < a1.capacity(); i++) {
            a1.enqueue(i);
            assertNotEquals(a1, a2);
            assertNotEquals(a2, a1);
            a2.enqueue(i);
            assertEquals(a1, a2);
        }
    }

    @Test(expected = ConcurrentModificationException.class)
    @Ignore
    public void testIteratorConcurrentModification() {
        BoundedQueue<Integer> q = new ArrayRingBuffer<>(16);
        q.enqueue(0);
        q.enqueue(1);
        Iterator<Integer> it = q.iterator();
        q.enqueue(2);
        it.next();
    }

    @Test
    @Ignore
    public void testIteratorRemove() {
        BoundedQueue<Integer> q = new ArrayRingBuffer<>(16);
        q.enqueue(0);
        q.enqueue(1);
        Iterator<Integer> it = q.iterator();
        assertEquals(0, (int) it.next());
        it.remove();
        assertTrue(it.hasNext());
        assertEquals(1, (int) it.next());
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("Expected it.next() to throw NoSuchElementException");
        } catch (NoSuchElementException e) {
            // ignore
        }
        assertEquals(1, (int) q.peek());
        assertEquals(1, q.fillCount());
    }

    @Test
    @Ignore
    public void testIteratorRemoveThrows() {
        BoundedQueue<Integer> q = new ArrayRingBuffer<>(16);
        q.enqueue(0);
        q.enqueue(1);
        Iterator<Integer> it = q.iterator();
        try {
            it.remove();
            fail("Expected it.remove() to throw IllegalStateException");
        } catch (IllegalStateException e) {
            // ignore
        }
        while (!q.isEmpty()) {
            it.next();
            it.remove();
            try {
                it.remove();
                fail("Expected it.remove() to throw IllegalStateException");
            } catch (IllegalStateException e) {
                // ignore
            }
        }
        assertFalse(it.hasNext());
    }

    @Test
    public void testIteratorIndependence() {
        // This is a randomized test
        BoundedQueue<Integer> q = new ArrayRingBuffer<>(16);
        for (int i = 0; !q.isFull(); i++) {
            q.enqueue(i);
        }

        int i1 = 0, i2 = 0;
        Iterator<Integer> it1 = q.iterator(), it2 = q.iterator();
        while (true) {
            if (Math.random() < 0.5) {
                assertTrue(it1.hasNext());
                assertEquals(i1++, (int) it1.next());
                if (i1 == q.fillCount()) {
                    assertFalse(it1.hasNext());
                    break;
                }
            } else {
                assertTrue(it2.hasNext());
                assertEquals(i2++, (int) it2.next());
                if (i2 == q.fillCount()) {
                    assertFalse(it2.hasNext());
                    break;
                }
            }
        }
        while (i1 < q.fillCount()) {
            assertTrue(it1.hasNext());
            assertEquals(i1++, (int) it1.next());
        }
        while (i2 < q.fillCount()) {
            assertTrue(it2.hasNext());
            assertEquals(i2++, (int) it2.next());
        }
        assertFalse(it1.hasNext());
        assertFalse(it2.hasNext());
    }
}