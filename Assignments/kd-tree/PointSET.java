import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.List;

/**
 * Created by GanHong on 11/01/2017.
 */
public class PointSET {

    private TreeSet<Point2D> set;

    public PointSET() {
        // construct an empty set of points
        set = new TreeSet<>();
    }

    public boolean isEmpty() {
        // is the set empty?
        return set.isEmpty();
    }

    public int size() {
        // number of points in the set
        return set.size();
    }

    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (p == null) throw new NullPointerException("Null Argument");
        if(!set.contains(p)) set.add(p);
    }

    public boolean contains(Point2D p) {
        // does the set contain point p?
        if (p == null) throw new NullPointerException("Null Argument");
        return set.contains(p);
    }

    public void draw() {
        // draw all points to standard draw
        for (Point2D p : set) {
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle
        if (rect == null) throw new NullPointerException("Null Argument");
        List<Point2D> ret = new ArrayList<>();
        for (Point2D p : set) {
            if (rect.contains(p)) ret.add(p);
        }
        return ret;
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new NullPointerException("Null Argument");
        Point2D ptr = null;
        double distance = Double.MAX_VALUE;

        if (isEmpty()) return null;

        for (Point2D point : set) {
            if (point.distanceTo(p) < distance) {
                ptr = point;
                distance = point.distanceTo(p);
            }
        }

        return ptr;
    }

    public static void main(String[] args) {
        // unit testing of the methods (optional)
    }
}
