import java.io.PrintWriter;

public class LinkedList {
    public Node head;

    // Constructor to initialize the linked list with a head node
    public LinkedList(Loan obj) {
        head = new Node(obj, null);
    }

    // Appends a new node with the given SportsCar object to the end of the linked list
    public void append(Loan obj) {
        Node curr = head;

        while (curr.next != null) {
            curr = curr.next;
        }

        curr.next = new Node(obj, null);
    }

    // Inserts a new node after the given SportsCar object if it exists in the list
    public boolean insertAfter(Loan obj, Loan objToInsert) {                  // WARNING!!! THIS METHOD MAY NEED SOME IMPROVEMENTS + MIGHT NEED TO MODIFY THE VARIABLE NAMING
        Node curr = head;
        while (curr != null) {
            if (curr.obj == obj) {                                 // Check if the object in the current node matches the given object (same reference)
                Node temp = curr.next;
                curr.next = new Node(objToInsert, temp);

                return true;
            }
            curr = curr.next;
        }

        return false;
    }

    // Gets the size of the linked list by counting nodes
    public int getSize() {
        int i = 0;
        Node curr = this.head;

        while (curr != null) {
            i++;
            curr = curr.next;
        }

        return i;
    }

    // Sorts the linked list using a comparator for sorting
    public static void bubbleSort(LinkedList list, int size) {
        LenderNameComparator comparator = new LenderNameComparator();
        boolean haveSwapped;

        do {
            haveSwapped = false;
            Node curr = list.head;

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
    public static void swap (LinkedList list, Node n1, Node n2) {
        // If n1 is the head of the list
        if (n1 == list.head) {
            list.head = n2;
            n1.next = n2.next;
            n2.next = n1;
            return;
        }

        Node prev = null;
        Node curr = list.head;

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
        Node curr = this.head;
        while (curr != null) {
            writer.println(curr.obj.toString());
            curr = curr.next;
        }
    }

    public boolean isEmpty() {
        return (head == null);
    }

    public void remove(Node n) {
    if (n == null || head == null) return;

    if (head == n) {
        head = head.next;
        return;
    }

    Node current = head;
    while (current.next != null && current.next != n) {
        current = current.next;
    }

    if (current.next == n) {
        current.next = n.next;
    }
}

}