/******************************************************************************
 *  Compilation:  javac Deque.java
 *  Execution:    java Deque
 *  Dependencies: 
 *  
 *  This problem uses a double linked list to implement a double-end queue, 
 *  which meet the performance requirements as follow,
 *  1）Each non-iterator operation take constant worst-case time.
 *  2）Iterator constructor takes constant worst-case time..
 *  3）Other iterator operations take constant worst-case time.
 *  Memory usage meets the requirement as,
 *  1）Non-iterator operations use memory linear in current number of items
 *  2）Memory per iterator use constant memory
 *  
 *  Author: Jian Shi
 *  Date:   Jan. 10 2016
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    private int n;         // number of elements on queue
    private Node first;    // beginning of queue
    private Node last;     // end of queue
    
    // helper linked list class
    private class Node {
        private Item item;
        private Node pre;
        private Node next;
    }
    
	// construct an empty deque
	public Deque() {
		first = null;
		last = null;
		n = 0;
		assert check();
	}
	
	// is the deque empty?
	public boolean isEmpty() {
		return first == null || last == null;
	}
	
	// return the number of items on the deque
	public int size() {
		return n;
	}
	
	// add the item to the front
	public void addFirst(Item item) {
		if(item == null) {
			throw new NullPointerException("Cannot add null item");
		}
        Node newfirst = new Node();
        newfirst.item = item;
        newfirst.pre = null;
        if (isEmpty()) {
        	first = newfirst;
        	last = first;
        } else {
        	newfirst.next = first;
        	first.pre = newfirst;
        	first = newfirst;
        }
        n++;
        assert check();
	}
	
	// add the item to the end
	public void addLast(Item item) {
		if(item == null) {
			throw new NullPointerException("Cannot add null item");
		}
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
        	first = last;
        	last.pre = null;
        } else {
        	oldlast.next = last;
        	last.pre = oldlast;
        }
        n++;
        assert check();		
	}
	
	// remove and return the item from the front
	public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) last = null;   // to avoid loitering
        else first.pre = null;
        assert check();
        return item;
	}
	
	// remove and return the item from the end
	public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = last.item;
        last = last.pre;
        n--;
        if (isEmpty()) first = null;   // to avoid loitering
        else last.next = null;
        assert check();
        return item;
	}

	// Copyright to algs4.cs.princeton.edu LinkedStack.java.
    /**
     * Returns a string representation of this queue.
     * @return the sequence of items in FIFO order, separated by spaces
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    } 
    // end copy
    
    // check internal invariants
    private boolean check() {
        if (n < 0) {
            return false;
        }
        else if (n == 0) {
            if (first != null) return false;
            if (last  != null) return false;
        }
        else if (n == 1) {
            if (first == null || last == null) return false;
            if (first != last)                 return false;
            if (first.next != null)            return false;
            if (last.pre != null)				return false;
        }
        else {
            if (first == null || last == null) return false;
            if (first == last)      return false;
            if (first.next == null) return false;
            if (last.next  != null) return false;
            if (first.pre  != null) return false;
            
            // check internal consistency of instance variable n
            int numberOfNodes = 0;
            for (Node x = first; x != null && numberOfNodes <= n; x = x.next) {
                numberOfNodes++;
            }
            if (numberOfNodes != n) return false;

            numberOfNodes = 0;
            for (Node x = last; x != null && numberOfNodes <= n; x = x.pre) {
                numberOfNodes++;
            }
            if (numberOfNodes != n) return false;
            
            // check internal consistency of instance variable last
            Node lastNode = first;
            while (lastNode.next != null) {
                lastNode = lastNode.next;
            }
            if (last != lastNode) return false;
            
            // check internal consistency of instance variable last
            Node firstNode = last;
            while (firstNode.pre != null) {
            	firstNode = firstNode.pre;
            }
            if (first != firstNode) return false;
        }

        return true;
    }
    
	// return an iterator over items in order from front to end
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}
	
    // an iterator, doesn't implement remove() since it's optional
    private class DequeIterator implements Iterator<Item> {
        private Node curr;

        public DequeIterator() {
        	curr = first;
        }

        public boolean hasNext() {
        	return curr != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
    		Item item = curr.item;
    		curr = curr.next;
    		return item;
        }
    }
    
	// unit testing (optional)
	public static void main(String[] args) {
		Deque<Integer> deq = new Deque<Integer>();
		statusPrint(deq);
		
		// add first from empty
		for(int i = 1; i <= 3; i++) {
			deq.addFirst(i);
			statusPrint(deq);
		}
		
		Iterator<Integer> iter = deq.iterator();
		System.out.println("Iterating through Deque:");
		while(iter.hasNext()) {
			System.out.print(iter.next() + " - ");
		}
		System.out.println();
		
		// remove first unitil empty
		while(!deq.isEmpty()) {
			deq.removeFirst();
			System.out.println("After remove first: ");
			statusPrint(deq);
		}
		
		// add last from empty
		for(int i = 1; i <= 3; i++) {
			deq.addLast(i);
			statusPrint(deq);
		}
		
		// remove last unitil empty
		while(!deq.isEmpty()) {
			deq.removeLast();
			System.out.println("After remove last: ");
			statusPrint(deq);
		}
	}
	
	private static void statusPrint(Deque deq) {
		System.out.println(deq);
		System.out.println("Then number of item in Deque is: " + deq.size());
	}
}
