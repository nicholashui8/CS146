import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// Takes a command-line integer k; reads in a sequence of strings from
// standard input; and prints out exactly k of them, uniformly at random.
// Note that each item from the sequence is printed out at most once.
public class Subset {
    public static void main(String[] args) {
        //read in integer which is the number of items we will be printing
        int k = Integer.parseInt(args[0]);
        ResizingArrayRandomQueue<String> randomQueue = new ResizingArrayRandomQueue<String>();

        //keep reading from command line until there is no more
        while (!StdIn.isEmpty()) {
            //read in string from command line and put it into queue
            randomQueue.enqueue(StdIn.readString());
        }
        StdOut.println();
        //randomly dequeue and printout k items from the queue

        for (int j = 0; j < k; j++) {
            StdOut.println(randomQueue.dequeue());
        }
    }
}
