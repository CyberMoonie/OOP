package oop.master.queue;
public interface Queue<T> {
    public void enqueue(T item);
    public T dequeue();
    public T peek();
    public boolean isEmpty();
    public int size();
}
