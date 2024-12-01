package oop.master.queue;

public class LinearQueue<T> implements Queue<T>{

    private Node<T> rear;
    private Node<T> front;
    private int size;

    // Constructor
    public LinearQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    @Override
    public void enqueue(T item) {
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
