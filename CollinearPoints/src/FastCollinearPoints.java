import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private final List<LineSegment> segList;

    private class Line {
        private final Point start;
        private final double slope;

        public Line(Point start, double slope) {
            this.start = start;
            this.slope = slope;
        }

        public boolean onLine(Point that) {
            if (Double.compare(start.slopeTo(that), Double.NEGATIVE_INFINITY) == 0)
                return true;
            else
                return Double.compare(slope, start.slopeTo(that)) == 0;
        }
        
        public double getSlope() {
            return slope;
        }
    }

    public FastCollinearPoints(Point[] points) // finds all line segments
                                               // containing 4 or more points
    {
        List<Point> pp = new ArrayList<Point>();
        double slope1, slope2, slope3;
        segList = new ArrayList<LineSegment>();
        List<Line> lineList = new ArrayList<Line>();

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
            for (int j = i + 1; j < n; j++) {
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

        int size = points.length;
        boolean exist = false;
        double[] slopes = new double[size];
        for (int i = 0; i < size; i++) {
            Arrays.sort(opp, points[i].slopeOrder());
            for (int j = 0; j < size; j++) {
                slopes[j] = points[i].slopeTo(opp[j]);
            }
            for (int j = 0; j < size - 2; j++) {
                slope1 = slopes[j];
                slope2 = slopes[j+1];
                slope3 = slopes[j+2];
                if (Double.compare(slope1, slope2) == 0  && Double.compare(slope1, slope3) == 0) {
                    pp.add(points[i]);
                    pp.add(opp[j]);
                    pp.add(opp[j + 1]);
                    pp.add(opp[j + 2]);
                    for (int k = j + 3; k < size; k++) {
                        slope3 = pp.get(0).slopeTo(opp[k]);
                        if (Double.compare(slope1, slope3) == 0) {
                            pp.add(opp[k]);
                            continue;
                        } else {
                            break;
                        }
                    }
                    for (Line line : lineList) {
                        if (line.onLine(pp.get(0)) && line.getSlope() == slope1) {
                            exist = true;
                        }
                    }
                    if (!exist) {
                        Collections.sort(pp);
                        segList.add(new LineSegment(pp.get(0), pp.get(pp.size()-1)));
                        lineList.add(new Line(pp.get(0), slope1));
                    }
                    exist = false;
                    pp.clear();
                }
            }
        }
    }

    public int numberOfSegments() // the number of line segments
    {
        return segList.size();
    }

    public LineSegment[] segments() // the line segments
    {
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
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}