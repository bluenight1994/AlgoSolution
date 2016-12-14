package com.hong.algo;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

/**
 * Created by GanHong on 12/13/16.
 */
public class FastCollinearPoints {

    private int num = 0;
    private List<LineSegment> seq;

    public FastCollinearPoints(Point[] points) {
        Arrays.sort(points);
        for(int i=0; i<points.length; i++) {
            Point originalPoint = points[i];
            Point[] other = new Point[points.length-1];
        }

    }

    public int numberOfSegments() {
        return num;
    }

    public LineSegment[] segments() {
        return (LineSegment[]) seq.toArray();
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
