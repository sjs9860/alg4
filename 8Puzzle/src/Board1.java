import java.util.ArrayList;
import java.util.List;

public class Board {
    private static int[][] man;
    private final int[] board; // board representation
    private final int n; // dimension
    private int ma; // mahanttan distance
    private int h; // hamming distance
    private int l0; // location of blank

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.n = blocks.length;
        // calculate manhattan array
        if (man == null) {
            int ig, jg, ir, jr;
            Board.man = new int[n * n][n * n];
            // piece value, with goal board index as k-1
            for (int v = 1; v < n * n; v++) {
                // the real board index
                for (int k = 0; k < n * n; k++) {
                    ig = (v - 1) / n;
                    jg = (v - 1) % n;
                    ir = (k) / n;
                    jr = (k) % n;
                    Board.man[v][k] = Math.abs(ig - ir) + Math.abs(jg - jr);
                }
            }
        }
        this.ma = 0;
        this.l0 = 0;
        this.h = 0;
        this.board = new int[n * n];
        for (int k = 0; k < n * n; k++) {
            int i = k / n;
            int j = k % n;
            int v = blocks[i][j];
            board[k] = v;
            if (v == 0) {
                this.l0 = k;
            } else {
                if (v != k + 1) {
                    this.ma += Board.man[v][k];
                    this.h++;
                }
            }
        }
    }

    // board dimension n
    public int dimension() {
        return this.n;
    }

    // number of blocks out of place
    public int hamming() {
        return this.h;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return this.ma;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.h == 0;
    }

    // a board that is obtained by exchanging any pair of
    // blocks
    public Board twin() {
        int[][] nb = new int[n][n];
        for (int k = 0; k < n * n; k++) {
            int i = k / n;
            int j = k % n;
            nb[i][j] = this.board[k];
        }
        int i0 = this.l0/n;
        if(i0 != 0) {
            int t = nb[0][0];
            nb[0][0] = nb[0][1];
            nb[0][1] = t;
        }
        else {
            int t = nb[1][0];
            nb[1][0] = nb[1][1];
            nb[1][1] = t;            
        }
        return new Board(nb);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        Board that = (Board) y;
        for(int i = 0; i < n*n; i++) {
            if(this.board[i] != that.board[i]) {
                return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> list = new ArrayList<Board>();
        int i0 = this.l0/this.n;
        int j0 = this.l0%this.n;
        int[][] nb = new int[n][n];
        for (int k = 0; k < n * n; k++) {
            int i = k / n;
            int j = k % n;
            nb[i][j] = this.board[k];
        }
        // find neighbors
        if(i0-1 > -1) {
            nb[i0][j0] = nb[i0-1][j0];
            nb[i0-1][j0] = 0;
            list.add(new Board(nb));
            nb[i0-1][j0] = nb[i0][j0];
            nb[i0][j0] = 0;
        }
        if(j0+1 < n) {
            nb[i0][j0] = nb[i0][j0+1];
            nb[i0][j0+1] = 0;
            list.add(new Board(nb));
            nb[i0][j0+1] = nb[i0][j0];
            nb[i0][j0] = 0;
        }
        if(j0-1 > -1) {
            nb[i0][j0] = nb[i0][j0-1];
            nb[i0][j0-1] = 0;
            list.add(new Board(nb));
            nb[i0][j0-1] = nb[i0][j0];
            nb[i0][j0] = 0;
        }
        if(i0+1 < n) {
            nb[i0][j0] = nb[i0+1][j0];
            nb[i0+1][j0] = 0;
            list.add(new Board(nb));
            nb[i0+1][j0] = nb[i0][j0];
            nb[i0][j0] = 0;
        }
        return list;
    }

    // string representation of this board (in the
    // output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", this.board[i*n+j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) // unit tests (not graded)
    {
        int n = 3;
        int[][] b = { { 1, 0 }, { 2, 3 } };
        n = 2;
        Board test = new Board(b);
        System.out.println(test);
        System.out.println(test.twin());
        for(Board bd: test.neighbors()) {
            System.out.println("Neighbor equals test: "+ test.equals(bd));
            System.out.println(bd);
        }
    }
}
