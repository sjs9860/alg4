import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private Node root;
    private int size;

    private class Node {
        private Point2D val;
        private Node left, right;

        public Node(Point2D v) {
            val = v;
            left = null;
            right = null;
        }
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException();
        root = insert(root, p, true);
    }

    private Node insert(Node root, Point2D p, boolean divideByX) {
        if (root == null) {
            root = new Node(p);
            size++;
            return root;
        }
        if (root.val.equals(p))
            return root;
        int cmp;
        if (divideByX) {
            cmp = Double.compare(p.x(), root.val.x());
        } else {
            cmp = Double.compare(p.y(), root.val.y());
        }
        if (cmp < 0) {
            root.left = insert(root.left, p, !divideByX);
        } else {
            root.right = insert(root.right, p, !divideByX);
        }
        return root;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException();
        return get(root, p, true);
    }

    private boolean get(Node root, Point2D p, boolean divideByX) {
        if (root == null) {
            return false;
        }
        int cmp;
        if (divideByX) {
            cmp = Double.compare(p.x(), root.val.x());
            if (cmp < 0) {
                return get(root.left, p, !divideByX);
            } else if (cmp == 0) {
                if (Double.compare(p.y(), root.val.y()) == 0)
                    return true;
                else
                    return get(root.right, p, !divideByX);
            } else {
                return get(root.right, p, !divideByX);
            }
        } else {
            cmp = Double.compare(p.y(), root.val.y());
            if (cmp < 0) {
                return get(root.left, p, !divideByX);
            } else if (cmp == 0) {
                if (Double.compare(p.x(), root.val.x()) == 0)
                    return true;
                else
                    return get(root.right, p, !divideByX);
            } else {
                return get(root.right, p, !divideByX);
            }
        }
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, true, 0, 0, 1, 1);
    }

    private void draw(Node root, boolean divideByX, double xmin, double ymin,
            double xmax, double ymax) {
        if (root == null) {
            return;
        }
        Point2D p = root.val;
        StdDraw.setPenColor(StdDraw.BLACK);
        p.draw();
        if (divideByX) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(p.x(), ymin, p.x(), ymax);
            draw(root.left, !divideByX, xmin, ymin, p.x(), ymax);
            draw(root.right, !divideByX, p.x(), ymin, xmax, ymax);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(xmin, p.y(), xmax, p.y());
            draw(root.left, !divideByX, xmin, ymin, xmax, p.y());
            draw(root.right, !divideByX, xmin, p.y(), xmax, ymax);
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        return range(root, rect, true, new LinkedList<Point2D>());
    }

    private Iterable<Point2D> range(Node root, RectHV rect, boolean divideByX,
            List<Point2D> re) {
        if (root == null) {
            return re;
        }
        if (rect.contains(root.val)) {
            re.add(root.val);
        }
        if (divideByX) {
            if (rect.xmax() < root.val.x()) {
                range(root.left, rect, !divideByX, re);
            } else {
                range(root.right, rect, !divideByX, re);
                range(root.left, rect, !divideByX, re);
            }
        } else {
            if (rect.ymax() < root.val.y()) {
                range(root.left, rect, !divideByX, re);
            } else {
                range(root.right, rect, !divideByX, re);
                range(root.left, rect, !divideByX, re);
            }
        }
        return re;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new java.lang.NullPointerException();
        RectHV box = new RectHV(0, 0, 1, 1);
        if (root == null || !box.contains(p)) {
            return null;
        }
        return nearest(root, p, true, root.val);
    }

    private Point2D nearest(Node root, Point2D p, boolean divideByX,
            Point2D mp) {
        if (root == null)
            return mp;
        Point2D rp = root.val;
        Point2D re = mp;
        double md = p.distanceTo(mp);
        double d = p.distanceTo(rp);
        if (d < md) {
            md = d;
            re = rp;
        }

        Point2D lm = null;
        Point2D rm = null;
        double ld = Double.MAX_VALUE;
        double rd = Double.MAX_VALUE;
        int cmp;
        double dd;
        if (divideByX) {
            cmp = Double.compare(p.x(), rp.x());
            dd = p.x() - rp.x();
        } else {
            cmp = Double.compare(p.y(), rp.y());
            dd = p.y() - rp.y();
        }
        if (cmp < 0) {
            lm = nearest(root.left, p, !divideByX, re);
            ld = p.distanceTo(lm);
            if (ld < md) {
                md = ld;
                re = lm;
            }
            if (ld < Math.abs(dd)) {
                re = lm;
            } else {
                rm = nearest(root.right, p, !divideByX, re);
                rd = p.distanceTo(rm);
                if (rd < md) {
                    md = rd;
                    re = rm;
                }
            }
        } else {
            rm = nearest(root.right, p, !divideByX, re);
            rd = p.distanceTo(rm);
            if (rd < md) {
                md = rd;
                re = rm;
            }
            if (rd < Math.abs(dd)) {
                re = rm;
            } else {
                lm = nearest(root.left, p, !divideByX, re);
                ld = p.distanceTo(lm);
                if (ld < md) {
                    md = ld;
                    re = lm;
                }
            }
        }
        return re;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
