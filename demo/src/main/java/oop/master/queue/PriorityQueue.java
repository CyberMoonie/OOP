package oop.master.queue;

import java.util.Comparator;

public class PriorityQueue<T> implements Queue<T> {
    private Node<T> front;
    private Node<T> rear;
    private final Comparator<T> comparator;
    private int size;

    /*
     * new Comparator<Car>() {
        @Override
        public int compare(Car c1, Car c2) {
            return Integer.compare(c1.getConsumption(), c2.getConsumption());
            }
        }
     */

    // Constructor with comparator parameter
    public PriorityQueue(Comparator<T> comparator) {
        this.front = null;
        this.rear = null;
        this.comparator = comparator;
        this.size = 0;
    }
    @Override
    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
    
        if (rear == null) {
            front = newNode;
            rear = newNode;
        } else {
            Node<T> current = front;
            Node<T> previous = null;

            while (current != null && !(comparator.compare(current.data, item) < 0)) {
                previous = current;
                current = current.next;
            }
    
            if (previous == null) {
                newNode.next = front;
                front = newNode;
            } else {
                previous.next = newNode;
                newNode.next = current;
            }
            if (current == null) {
                rear = newNode;
            }
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
