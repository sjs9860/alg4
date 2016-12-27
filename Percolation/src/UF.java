/**********************************************************
 *	Class UF
 *  Implement disjoint set introduced by Algorithms I
 * 
 *********************************************************/
public class UF {
	private int[] id;
	private int[] sz;
	// A quick union implementation
	// with weighting
	public UF(int N) {
		id = new int[N];
		sz = new int[N];
		for(int i = 0; i < N; i++) {
			id[i] = i;
			sz[i] = 1;
		}
	}
	
	public void union(int p, int q) {
		int i = root(p);
		int j = root(q);
		if(sz[i] >= sz[j]) id[j] = i;
		else id[i] = j;
	}
	
	public boolean connected(int p, int q) {
		return root(p) == root(q);
	}
	
	private int root(int i) {
		while(i != id[i]) {
			i = id[i];
		}
		return i;
	}
}
