import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {

    private Set<Point2D> pset;

    // construct an empty set of points
    public PointSET() {
        pset = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pset.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pset.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if(p == null)
            throw new IllegalArgumentException("Param p is null");
        pset.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if(p == null)
            throw new IllegalArgumentException("Param p is null");
        return pset.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : pset) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if(rect == null)
            throw new IllegalArgumentException("Param rect is null");
        Set<Point2D> re = new TreeSet<Point2D>();
        for (Point2D p : pset) {
            if (rect.contains(p)) {
                re.add(p);
            }
        }
        return re;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if(p == null)
            throw new IllegalArgumentException("Param p is null");
        double min = Double.MAX_VALUE;
        Point2D re = null;
        for (Point2D pt : pset) {
            double d = pt.distanceSquaredTo(p);
            if (min > d) {
                min = d;
                re = pt;
            }
        }
        return re;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}