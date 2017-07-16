import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private final List<LineSegment> segList;

    // For simplicity, we will not supply any input to BruteCollinearPoints that
    // has 5 or more collinear points.
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        Point pp1, pp2, pp3, pp4;
        double slope1, slope2, slope3;
        segList = new ArrayList<LineSegment>();
        if (points == null) {
            throw new IllegalArgumentException(
                    "Null param is passed to BruteCollinearPoints constructor");
        }

        int n = points.length;
        Point[] opp = new Point[n];
        for (int i = 0; i < n; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException(
                        "Null element in points array");
            }
            for (int j = i+1; j < n; j++) {
                if (points[j] == null) {
                    throw new IllegalArgumentException(
                            "Null element in points array");
                }
                if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException(
                            "argument to the constructor contains a repeated point");
                }
            }
            opp[i] = points[i];
        }

        Arrays.sort(opp);

        for (int p = 0; p < n; p++) {
            for (int q = p + 1; q < n; q++) {
                for (int r = q + 1; r < n; r++) {
                    for (int s = r + 1; s < n; s++) {
                        pp1 = opp[p];
                        pp2 = opp[q];
                        pp3 = opp[r];
                        pp4 = opp[s];
                        slope1 = pp1.slopeTo(pp2);
                        slope2 = pp1.slopeTo(pp3);
                        slope3 = pp1.slopeTo(pp4);
                        if (Double.compare(slope1, slope2) == 0  && Double.compare(slope1, slope3) == 0) {
                            segList.add(new LineSegment(pp1, pp4));
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segList.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[segList.size()];
        result = segList.toArray(result);
        return result;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-100, 32768 + 100);
        StdDraw.setYscale(-100, 32768 + 100);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
