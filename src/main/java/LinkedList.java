   // LinkedList data structure created to find children of best node
    public class LinkedList {
        protected Node head;
        protected Node tail;

        public LinkedList() { // Constructor
            head = null;
            tail = null;
        }

        /**
         * inserts a node at the end of a linked list
         * @param newNode inserted node
         */
        public void insertLast(Node newNode) {
            if (head == null) {
                head = newNode;
            } else {
                tail.setNext(newNode);
            }
            tail = newNode;
        }

        /**
         * returns the ith node in a linked list
         * @param i ith index to be retrieved
         * @return node in the ith index. returns null if this index does not exist.
         */
        public Node getNodeI(int i) {
            Node temp = head;
            int index = 0;
            while (temp != null) {
                if (index == i) {
                    return temp;
                }
                temp = temp.getNext();
                index++;
            }
            return null;
        }

        /**
         * finds the number of elements in a linked list
         * @return number of elements in a linked list
         */
        public int numberOfElements() {
            Node temp = head;
            int count = 0;
            while (temp != null) {
                count++;
                temp = temp.getNext();
            }
            return count;
        }
}
