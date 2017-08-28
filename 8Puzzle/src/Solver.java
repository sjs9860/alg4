import java.util.Deque;
import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private class SearchNode implements Comparable<SearchNode> {
        SearchNode preNode;
        Board nowBoard;
        int m; // moves done

        public SearchNode(Board now, SearchNode pre) {
            this.nowBoard = now;
            this.preNode = pre;
        }

        public SearchNode(Board now) {
            this(now, null);
        }

        public int compareTo(SearchNode that) {
            return this.nowBoard.manhattan() - that.nowBoard.manhattan()
                    + this.m - that.m;
        }
    }

    private SearchNode original;
    private SearchNode twin;
    private SearchNode end;
    private SearchNode tend;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Error! Initial board is null");
        }
        MinPQ<SearchNode> pqo = new MinPQ<SearchNode>();
        MinPQ<SearchNode> pqt = new MinPQ<SearchNode>();

        SearchNode op, tp;
        end = null;

        original = new SearchNode(initial);
        original.m = 0;
        twin = new SearchNode(initial.twin());
        twin.m = 0;
        pqo.insert(original);
        pqt.insert(twin);
        do {
            op = pqo.delMin();
            tp = pqt.delMin();

            if (op.nowBoard.isGoal()) {
                end = op;
                continue;
            }
            op.m++;
            for (Board n : op.nowBoard.neighbors()) {
                if (op.preNode != null && n.equals(op.preNode.nowBoard)) {
                    continue;
                }
                original = new SearchNode(n);
                original.m = op.m;
                original.preNode = op;
                pqo.insert(original);
            }

            if (tp.nowBoard.isGoal()) {
                tend = tp;
                continue;
            }
            tp.m++;
            for (Board n : tp.nowBoard.neighbors()) {
                if (tp.preNode != null && n.equals(tp.preNode.nowBoard)) {
                    continue;
                }
                twin = new SearchNode(n);
                twin.m = tp.m;
                twin.preNode = tp;
                pqt.insert(twin);

            }
        } while ((end == null) && (tend == null));
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return this.end != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable() ? this.end.m : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        Deque<Board> sol = new LinkedList<Board>();
        SearchNode op = end;
        if (isSolvable()) {
            while (op != null) {
                sol.addFirst(op.nowBoard);
                op = op.preNode;
            }
            return sol;
        }
        return null;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}