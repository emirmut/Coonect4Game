// Node of the linked list
public class Node {
    protected int data;
    protected Node next;

    public Node(int data) { // Constructor
        this.data = data;
        this.next = null;
    }

    public void setNext(Node next) { // setter
        this.next = next;
    }

    public Node getNext() { // getter
        return next;
    }

    public int getData() { // getter
        return data;
    }
}
