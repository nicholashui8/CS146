import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Random queue implementation using a resizing array.
public class ResizingArrayRandomQueue<Item> implements Iterable<Item> {
    //CODE HERE
    private Item[] array;
    //pointer to the last item in the array (most recently added item)
    private int lastItem;

    // Construct an empty queue.
    public ResizingArrayRandomQueue() {
        this.array = (Item[]) new Object[1];
        this.lastItem = -1;
    }

    // Is the queue empty?
    public boolean isEmpty() {
        if (this.lastItem == -1)
            return true;
        return false;
    }

    // The number of items on the queue.
    public int size() {
        return this.lastItem + 1;
    }

    // Add item to the queue.
    public void enqueue(Item item) {
        if (item != null) {
            //if array is full, double the size of the array
            if (this.lastItem == this.array.length - 1) {
                resize(array.length * 2);
            }
            this.lastItem++;
            array[this.lastItem] = item;
        } else {
            throw new NullPointerException();
        }

    }

    // Remove and return a random item from the queue.
    public Item dequeue() {
        if (lastItem >= 0) {
            //generate random index to remove an item
            int randomIndex = StdRandom.uniform(0, lastItem + 1);
            Item randomItem = array[randomIndex];

            //move item at the last index to the spot where we just removed an item
            array[randomIndex] = array[lastItem];
            array[lastItem] = null;
            lastItem--;
            return randomItem;
        }
        throw new NoSuchElementException();

    }

    // Return a random item from the queue, but do not remove it.
    public Item sample() {
        if (lastItem >= 0) {
            int randomIndex = StdRandom.uniform(0, lastItem);
            Item randomItem = array[randomIndex];
            return randomItem;
        }
        throw new NoSuchElementException();
    }

    // An independent iterator over items in the queue in random order.
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    // An iterator, doesn't implement remove() since it's optional.
    private class RandomQueueIterator implements Iterator<Item> {
        //copy of orginal array so that each iterator has its own random order
        Item[] copy;
        int copyLastItem;

        RandomQueueIterator() {
            this.copy = (Item[]) new Object[lastItem + 1];
            for (int i = 0; i < lastItem + 1; i++) {
                copy[i] = array[i];
            }
            this.copyLastItem = lastItem;
        }

        public boolean hasNext() { //CODE HERE
            if (copyLastItem < 0)
                return false;
            return true;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (hasNext()) {
                int randomIndex = StdRandom.uniform(copyLastItem + 1);
                Item randomItem = copy[randomIndex];
                copy[randomIndex] = copy[copyLastItem];
                copy[copyLastItem] = null;
                copyLastItem--;
                return randomItem;
            }
            throw new NoSuchElementException();
        }
    }

    // A string representation of the queue.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this.array) {
            s.append(item + " ");
        }
        return s.toString().substring(0, s.length() - 1);
    }

    // Helper method for resizing the underlying array.
    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i <= lastItem; i++) {
            if (array[i] != null) {
                temp[i] = array[i];
            }
        }
        array = temp;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        ResizingArrayRandomQueue<Integer> q =
                new ResizingArrayRandomQueue<Integer>();
        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readInt());
        }
        int sum1 = 0;
        for (int x : q) {
            System.out.println("val: " + x);
            sum1 += x;
        }
        int sum2 = sum1;
        for (int x : q) {
            sum2 -= x;
        }
        int sum3 = 0;
        while (q.size() > 0) {
            sum3 += q.dequeue();
        }
        StdOut.println(sum1);
        StdOut.println(sum2);
        StdOut.println(sum3);
        StdOut.println(q.isEmpty());
    }
}
