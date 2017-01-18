import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by GanHong on 12/13/16.
 */
public class FastCollinearPoints {

    private int num = 0;
    private List<LineSegment> seq;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        for (Point p : points) {
            if (p == null) throw new NullPointerException();
        }
        Arrays.sort(points);
        for (int i = 0; i<points.length-1; i++) {
            if (points[i].compareTo(points[i+1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
        seq = new ArrayList<>();
        for(int i=0; i<points.length; i++) {
            Point originalPoint = points[i];
            Point[] other = new Point[points.length-1];
            int index = 0;
            for (int j = 0; j < points.length; j++) {
                if (j != i) {
                    other[index++] = points[j];
                }
            }
            Arrays.sort(other, originalPoint.slopeOrder());
            List<Point> collinear = new ArrayList<>();
            double previous = 0.0;
            for (Point p : other) {
                if (p.slopeTo(originalPoint) != previous) {
                    if (collinear.size() >= 3) {
                        collinear.add(originalPoint);
                        Collections.sort(collinear);
                        if (originalPoint.compareTo(collinear.get(0)) == 0) {
                            LineSegment segment = new LineSegment(collinear.get(0), collinear.get(collinear.size()-1));
                            seq.add(segment);
                        }
                    }
                    collinear.clear();
                }
                collinear.add(p);
                previous = p.slopeTo(originalPoint);
            }
            if (collinear.size() >= 3) {
                collinear.add(originalPoint);
                Collections.sort(collinear);
                if (originalPoint.compareTo(collinear.get(0)) == 0) {
                    LineSegment segment = new LineSegment(collinear.get(0), collinear.get(collinear.size()-1));
                    seq.add(segment);
                }
            }
        }
    }

    public int numberOfSegments() {
        return num;
    }

    public LineSegment[] segments() {
        return seq.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
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
