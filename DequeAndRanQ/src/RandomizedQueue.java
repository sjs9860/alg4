/******************************************************************************
 *  Compilation:  javac RandomizedQueue.java
 *  Execution:    java RandomizedQueue
 *  Dependencies: StdRandom.java
 *
 *  This program uses resizable array to implement a randomized queue structure.
 *  The randomized queue is based on a resizable array. The time complexity
 *  meets,
 *  1）Each non-iterator operation take constant amortized time.
 *  2）Iterator constructor takes linear time of number of elements in this queue.
 *  3）Other iterator operations take constant worst-case time.
 *  Memory usage meets the requirement as,
 *  1）Non-iterator operations use memory linear in current number of items
 *  2）Memory per iterator use memory linear in current number of items
 *  
 *  Author: Jian Shi
 *  Date:   Jan. 10 2016
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a; // array of items
    private int n; // number of elements on stack

    // construct an empty randomized queue
    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        n = 0;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the queue
    public int size() {
        return n;
    }

    // Copyright to algs4.cs.princeton.edu ResizingArrayStack.java.
    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= n;

        // textbook implementation
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;

        // alternative implementation
        // a = java.util.Arrays.copyOf(a, capacity);
    }

    // end copy

    // add the item
    public void enqueue(Item item) {
        if (n == a.length)
            resize(2 * n);
        a[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Randomized Queue underflow");
        int randomIndex = StdRandom.uniform(n);
        Item item = a[randomIndex];
        if (randomIndex != n - 1) {
            a[randomIndex] = a[n - 1];
        }
        a[n - 1] = null;
        n--;
        if (n > 0 && n == a.length / 4) {
            resize(a.length / 2);
        }
        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("Randomized Queue underflow");
        int randomIndex = StdRandom.uniform(n);
        return a[randomIndex];
    }

    // Copyright to algs4.cs.princeton.edu LinkedStack.java.
    /**
     * Returns a string representation of this queue.
     * 
     * @return the sequence of items in FIFO order, separated by spaces
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }

    // end copy

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    private class RandomQueueIterator implements Iterator<Item> {
        // Item array to store all current items
        private Item[] iterArr;
        // Number of current items
        private int iterCap;

        public RandomQueueIterator() {
            iterCap = n;
            iterArr = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) {
                iterArr[i] = a[i];
            }
        }

        public boolean hasNext() {
            return iterCap > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (isEmpty())
                throw new NoSuchElementException("Randomized Queue underflow");
            int length = iterArr.length;
            int index = StdRandom.uniform(iterCap);
            Item item = iterArr[index];
            if (index < iterCap - 1) {
                iterArr[index] = iterArr[iterCap - 1];
            }
            iterArr[iterCap - 1] = null;
            iterCap--;
            return item;
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

        // enque 0 to 9
        for (int i = 0; i < 10; i++) {
            rq.enqueue(i);
        }

        // iterator test
        Iterator<Integer> iter = rq.iterator();
        while (iter.hasNext()) {
            System.out.print(iter.next());
        }
        System.out.println();

        rq.dequeue();
        rq.dequeue();

        // another independent iterator
        Iterator<Integer> iter2 = rq.iterator();
        while (iter2.hasNext()) {
            System.out.print(iter2.next());
        }
        System.out.println();

        // sample test
        for (int i = 0; i < 3; i++) {
            System.out.println((i + 1) + ") 4 consecutive samples");
            for (int j = 0; j < 4; j++) {
                System.out.print(rq.sample() + " ");
            }
            System.out.print("\n");
        }

        // dequeue test
        while (!rq.isEmpty()) {
            rq.dequeue();
            System.out.println();
        }
    }
}
