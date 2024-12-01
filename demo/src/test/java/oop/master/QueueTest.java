package oop.master;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import oop.master.queue.LimitQueue;
import oop.master.queue.LinearQueue;
import oop.master.queue.PriorityQueue;
import oop.master.queue.Queue;
class QueueTest {

    // Test for LinearQueue
    @Test
    void testEnqueueAndDequeueLinearQueue() {
        Queue<Integer> queue = new LinearQueue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
    }

    @Test
    void testPeekLinearQueue() {
        Queue<String> queue = new LinearQueue<>();
        queue.enqueue("A");
        assertEquals("A", queue.peek());
        assertFalse(queue.isEmpty());
    }

    @Test
    void testIsEmptyLinearQueue() {
        Queue<Integer> queue = new LinearQueue<>();
        assertTrue(queue.isEmpty());
        queue.enqueue(50);
        assertFalse(queue.isEmpty());
    }

    @Test
    void testSizeLinearQueue() {
        Queue<Integer> queue = new LinearQueue<>();
        queue.enqueue(10);
        queue.enqueue(20);
        assertEquals(2, queue.size());
        queue.dequeue();
        queue.dequeue();
        assertEquals(0, queue.size());
    }

    // Test for DoubleQueue
    @Test
    void testEnqueueAndDequeueLimitQueue() {
        Queue<Integer> queue = new LimitQueue<>(10);
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
    }

    @Test
    void testPeekLimitQueue() {
        Queue<String> queue = new LimitQueue<>(10);
        queue.enqueue("A");
        assertEquals("A", queue.peek());
        assertFalse(queue.isEmpty());
    }

    @Test
    void testIsEmptyLimitQueue() {
        Queue<Integer> queue = new LimitQueue<>(10);
        assertTrue(queue.isEmpty());
        queue.enqueue(5);
        assertFalse(queue.isEmpty());
    }

    @Test
    void testSizeLimitQueue() {
        Queue<Integer> queue = new LimitQueue<>(3);
        queue.enqueue(10);
        queue.enqueue(20);
        assertEquals(2, queue.size());
        queue.enqueue(50);
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        assertEquals(0, queue.size());
    }
    @Test
    void testSizeLimitQueueBounds() {
        Queue<Integer> queue = new LimitQueue<>(1);
        queue.enqueue(10);
        Exception e = assertThrowsExactly(IllegalStateException.class, () -> queue.enqueue(200));
        System.out.println(e.getMessage());
        queue.dequeue();
        e = assertThrowsExactly(IllegalStateException.class, () -> queue.dequeue());
        System.out.println(e.getMessage());


    }
    @Test
    void testPriorityQueueOrder() {
        Queue<Integer> queue = new PriorityQueue<>(Integer::compare);
        queue.enqueue(12);
        queue.enqueue(30);
        queue.enqueue(10);
        queue.enqueue(50);
        assertEquals(50, queue.dequeue());
        assertEquals(30, queue.dequeue());
        assertEquals(12, queue.dequeue());
        assertEquals(10, queue.dequeue());
        Exception e = assertThrowsExactly(IllegalStateException.class, () -> queue.dequeue());
        System.out.println(e.getMessage());

    }
}
