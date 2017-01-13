/******************************************************************************
 *  Compilation:  javac Permutation.java
 *  Execution:    java Permutation
 *  Dependencies: StdIn.java
 *
 *  This program takes a command-line integer k; reads in a sequence of strings
 *  from standard input using StdIn.readString(); and prints exactly k of them,
 *  uniformly at random. Print each item from the sequence at most once. 
 *  PRE: 0 <= k <= number of strings in sequence
 *  usage:
 *  % more distinct.txt                        % more duplicates.txt
 *	A B C D E F G H I                          AA BB BB BB BB BB CC CC
 *
 *	% java Permutation 3 < distinct.txt       % java Permutation 8 < duplicates.txt
 * 	C                                          BB
 *	G                                          AA
 *	A                                          BB
 *                                             CC
 *	% java Permutation 3 < distinct.txt        BB
 *	E                                          BB
 *	F                                          CC
 *	G                                          BB
 *  
 *  Author: Jian Shi
 *  Date:   Jan. 10 2016
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		int count = 0;
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		while(!StdIn.isEmpty()) {
			if(rq.size() == k) {
				if(count < k) {
					StdOut.println(rq.dequeue());
					count ++;
				} else {
					return;
				}
			}
			rq.enqueue(StdIn.readString());
		}
		while(count < k) {
			StdOut.println(rq.dequeue());
			count ++;
		}
	}
}
