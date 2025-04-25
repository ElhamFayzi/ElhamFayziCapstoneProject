public class U_QueueNode<T> {
    private T data;
    private U_QueueNode<T> next;

    public U_QueueNode(T data) {
        this.data = data;
        this.next = null;
    }

    public U_QueueNode(T data, U_QueueNode<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public U_QueueNode<T> getNext() {
        return next;
    }

    public void setNext(U_QueueNode<T> next) {
        this.next = next;
    }
}