import java.util.*;

// Define the Queue interface
public interface Queue<T> {
    void enqueue(T item); // Add an item to the queue
    T dequeue(); // Remove and return an item from the queue
    boolean isEmpty(); // Check if the queue is empty
    int size(); // Return the number of items in the queue
}

// Implementing a LinearQueue (a simple queue using LinkedList)
class LinearQueue<T> implements Queue<T> {
    private LinkedList<T> list = new LinkedList<>();

    @Override
    public void enqueue(T item) {
        list.addLast(item);
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return list.removeFirst();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int size() {
        return list.size();
    }
}

// Implementing a LimitQueue with a maximum capacity
class LimitQueue<T> implements Queue<T> {
    private LinkedList<T> list = new LinkedList<>();
    private int capacity;

    public LimitQueue(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void enqueue(T item) {
        if (size() >= capacity) {
            throw new IllegalStateException("Queue is full");
        }
        list.addLast(item);
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return list.removeFirst();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int size() {
        return list.size();
    }
}

// Implementing a PriorityQueue (items are ordered based on their priority)
class PriorityQueue<T> implements Queue<T> {
    private java.util.PriorityQueue<T> queue;

    public PriorityQueue(Comparator<? super T> comparator) {
        queue = new java.util.PriorityQueue<>(comparator);
    }

    @Override
    public void enqueue(T item) {
        queue.add(item);
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return queue.poll();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public int size() {
        return queue.size();
    }
}
