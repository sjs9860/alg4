/**********************************************************
 *  Compilation:  javac Testdriver.java
 *  Execution:    java Testdriver
 *  Dependencies: StdIn.java
 *
 *  This program test the UF data structure
 * 
 *********************************************************/
import edu.princeton.cs.algs4.StdIn;

public class Testdriver {
	public static void main(String[] args) {
		int N = StdIn.readInt();
		UF uf = new UF(N);
		while(!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if(!uf.connected(p, q)) {
				uf.union(p, q);
				System.out.println(p + " " + q);
			} else {
				System.out.println(p + " " + q + " are connected");
			}
		}
	}
}
