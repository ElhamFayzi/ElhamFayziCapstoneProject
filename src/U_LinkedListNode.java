public class U_LinkedListNode {
    public Loan obj;
    public U_LinkedListNode next;

    public U_LinkedListNode(Loan obj) {
        this.obj = obj;
        this.next = null;
    }

    public U_LinkedListNode(Loan obj, U_LinkedListNode next) {
        this.obj = obj;
        this.next = next;
    }

    public void setNext (U_LinkedListNode n) {
        this.next = n;
    }
}