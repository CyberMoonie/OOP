package oop.master.queue;

public class LimitQueue<T> implements Queue<T>{

    private Node<T> rear;
    private Node<T> front;
    private final int limitSize;
    private int size;

    // Constructor
    public LimitQueue(int limitSize) {
        this.front = null;
        this.rear = null;
        this.limitSize = limitSize;
        this.size = 0;
    }

    @Override
    public void enqueue(T item) {
        if (this.size() == limitSize){
            throw new IllegalStateException("Size limit reached");
        }
        Node<T> newNode = new Node<>(item);
        if (rear == null) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty.");
        }
        T item = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return item;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty.");
        }
        return front.data;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }
}
