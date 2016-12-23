package com.hong.algo;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import javax.sound.sampled.Line;
import java.util.*;

/**
 * Created by GanHong on 12/13/16.
 */
public class BruteCollinearPoints {

    private int num = 0;
    private List<LineSegment> seq;

    public BruteCollinearPoints(Point[] points) {
          // finds all line segments containing 4 points
        seq = new ArrayList<>();
        Arrays.sort(points);
        for (int i = 0; i<points.length; i++) {
            for (int j=i+1; j<points.length; j++) {
                for (int k=j+1; k<points.length; k++) {
                    for (int l=k+1; l<points.length; l++) {
                        Point p = points[i], q = points[j], r = points[k], s = points[l];
                        if (p.slopeTo(q) == q.slopeTo(r) && q.slopeTo(r) == r.slopeTo(s)) {
                            num++;
                            LineSegment segment = new LineSegment(p, s);
                            seq.add(segment);
                        }
                    }
                }
            }
        }
        System.out.println(seq.size());
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
