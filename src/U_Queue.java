public class U_Queue<T> {
    private U_QueueNode<T> head;
    private U_QueueNode<T> tail;
    private int size;

    public U_Queue() {
        head = null;
        tail = null;
        size = 0;
    }

    public void enqueue(T item) {
        U_QueueNode<T> newNode = new U_QueueNode<>(item);
        if (tail != null) {
            tail.setNext(newNode);
        }
        tail = newNode;
        if (head == null) {
            head = newNode;
        }
        size++;
    }

    public T dequeue() {
        if (head == null) {
            return null;
        }
        T data = head.getData();
        head = head.getNext();
        if (head == null) {
            tail = null;
        }
        size--;
        return data;
    }

    public T peek() {
        if (head == null) {
            return null;
        }
        return head.getData();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }
}