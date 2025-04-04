public class Node {
    public Loan obj;
    public Node next;

    public Node(Loan obj) {
        this.obj = obj;
    }

    public Node(Loan obj, Node next) {
        this.obj = obj;
        this.next = next;
    }

    public void setNext (Node n) {
        this.next = n;
    }
}