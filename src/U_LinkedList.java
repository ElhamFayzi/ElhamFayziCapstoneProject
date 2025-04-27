import java.io.PrintWriter;

public class U_LinkedList {
    public U_LinkedListNode head;

    // Constructor to initialize the linked list with a head node
    public U_LinkedList(Loan obj) {
        head = new U_LinkedListNode(obj, null);
    }

    public void append(Loan obj) {
        U_LinkedListNode curr = head;

        while (curr.next != null) {
            curr = curr.next;
        }

        curr.next = new U_LinkedListNode(obj, null);
    }

    public boolean insertAfter(Loan obj, Loan objToInsert) {
        U_LinkedListNode curr = head;
        while (curr != null) {
            if (curr.obj == obj) {                                 // Check if the object in the current node matches the given object (same reference)
                U_LinkedListNode temp = curr.next;
                curr.next = new U_LinkedListNode(objToInsert, temp);

                return true;
            }
            curr = curr.next;
        }

        return false;
    }

    // Gets the size of the linked list by counting nodes
    public int getSize() {
        int i = 0;
        U_LinkedListNode curr = this.head;

        while (curr != null) {
            i++;
            curr = curr.next;
        }

        return i;
    }

    // Sorts the linked list using a comparator for sorting
    public static void bubbleSort(U_LinkedList list, int size) {
        C_LoanReqNameComparator comparator = new C_LoanReqNameComparator();
        boolean haveSwapped;

        do {
            haveSwapped = false;
            U_LinkedListNode curr = list.head;

            for (int i = 0; i < size - 1; i++) {
                if (comparator.compare(curr.obj, curr.next.obj) > 0) {
                    swap(list, curr, curr.next);
                    haveSwapped = true;
                }
                else {
                    curr = curr.next;                // Move to the next node only if no swap is needed; doing otherwise would skip an element if a swap occurred and curr is updated
                }
            }

            size--;                                 // Reduce the size after each pass as the largest element gets sorted
        } while (haveSwapped);
    }

    // Swaps two adjacent nodes in the list
    public static void swap (U_LinkedList list, U_LinkedListNode n1, U_LinkedListNode n2) {
        // If n1 is the head of the list
        if (n1 == list.head) {
            list.head = n2;
            n1.next = n2.next;
            n2.next = n1;
            return;
        }

        U_LinkedListNode prev = null;
        U_LinkedListNode curr = list.head;

        // Find the node before n1
        while (curr != null && curr != n1) {
            prev = curr;
            curr = curr.next;
        }

        prev.next = n2;
        n1.next = n2.next;
        n2.next = n1;
    }

    public void writeToPrintWriter (PrintWriter writer) {
        U_LinkedListNode curr = this.head;
        while (curr != null) {
            writer.println(curr.obj.toString());
            curr = curr.next;
        }
    }

    public boolean isEmpty() {
        return (head == null);
    }

    public void remove(U_LinkedListNode n) {
    if (n == null || head == null) return;

    if (head == n) {
        head = head.next;
        return;
    }

    U_LinkedListNode current = head;
    while (current.next != null && current.next != n) {
        current = current.next;
    }

    if (current.next == n) {
        current.next = n.next;
    }
}

}