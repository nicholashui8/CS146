import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Deque implementation using a linked list.
public class LinkedDeque<Item> implements Iterable<Item> {
    //head node keeps track of the beginning of the deque
    private Node head;
    //tail node keeps track of the beginning of the deque
    private Node tail;
    //keeps track of number of nodes in deque
    private int size;

    // Helper doubly-linked list class.
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    // Construct an empty deque.
    public LinkedDeque() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // Is the dequeue empty?
    public boolean isEmpty() {
        if (this.size == 0)
            return true;
        return false;
    }

    // The number of items on the deque.
    public int size() {
        return this.size;
    }

    // Add item to the front of the deque.
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node newNode = new Node();
        newNode.item = item;
        //if this is first item being added, set head and tail to current node
        if (this.head == null) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            //make new node point to head
            newNode.next = head;
            //head needs to point backwards since this is doubly linked
            this.head.prev = newNode;
            //make new node the new head
            this.head = newNode;
        }
        this.size++;
    }

    // Add item to the end of the deque.
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node newNode = new Node();
        newNode.item = item;

        //if this is first item being added, set head and tail to current node
        if (this.head == null) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            this.tail.next = newNode;
            newNode.prev = this.tail;
            this.tail = newNode;
            newNode.next = null;
        }
        this.size++;
    }

    // Remove and return item from the front of the deque.
    public Item removeFirst() {
        if (this.size == 0)
            throw new NoSuchElementException();

        Node removeThisNode = this.head;

        //if there's only 1 node, make sure to set head and tail to null after removing
        if (this.size == 1) {
            this.head = null;
            this.tail = null;
            this.size--;
            return removeThisNode.item;
        }
        //set head to second node in deque
        this.head = this.head.next;
        this.head.prev = null;
        removeThisNode.next = null;
        this.size--;

        return removeThisNode.item;
    }


    // Remove and return item from the end of the deque.
    public Item removeLast() {
        if (this.size == 0)
            throw new NoSuchElementException();

        Node removeThisNode = this.tail;

        //if there's only 1 node, make sure to set head and tail to null after removing
        if (this.size == 1) {
            this.head = null;
            this.tail = null;
            this.size--;
            return removeThisNode.item;
        }
        //set tail to second to last node
        this.tail = this.tail.prev;
        this.tail.next = null;
        removeThisNode.prev = null;

        this.size--;

        return removeThisNode.item;
    }


    // An iterator over items in the queue in order from front to end.
    public Iterator<Item> iterator() {
        return new DequeIterator(head);
    }

    // An iterator, doesn't implement remove() since it's optional.
    private class DequeIterator implements Iterator<Item> {
        Node current;

        DequeIterator(Node head) {
            this.current = head;
        }

        public boolean hasNext() {
            if (current != null)
                return true;
            return false;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (hasNext()) {
                Item item = current.item;
                this.current = current.next;
                return item;
            }

            throw new NoSuchElementException();
        }
    }

    // A string representation of the deque.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item + " ");
        }
        return s.toString().substring(0, s.length() - 1);
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        LinkedDeque<Character> deque = new LinkedDeque<Character>();
        String quote = "There is grandeur in this view of life, with its "
                + "several powers, having been originally breathed into a few "
                + "forms or into one; and that, whilst this planet has gone "
                + "cycling on according to the fixed law of gravity, from so "
                + "simple a beginning endless forms most beautiful and most "
                + "wonderful have been, and are being, evolved. ~ "
                + "Charles Darwin, The Origin of Species";

        int r = StdRandom.uniform(0, quote.length());
        for (int i = quote.substring(0, r).length() - 1; i >= 0; i--) {
            deque.addFirst(quote.charAt(i));
        }
        for (int i = 0; i < quote.substring(r).length(); i++) {
            deque.addLast(quote.charAt(r + i));
        }
        StdOut.println(deque.isEmpty());
        StdOut.printf("(%d characters) ", deque.size());

        for (char c : deque) {
            StdOut.print(c);
        }
        StdOut.println();
        double s = StdRandom.uniform();
        for (int i = 0; i < quote.length(); i++) {
            if (StdRandom.bernoulli(s)) {
                deque.removeFirst();
            } else {
                deque.removeLast();
            }
        }
        StdOut.println(deque.isEmpty());
    }
}
