package com.hong.algo;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Created by GanHong on 3/22/17.
 */
public class ShortestCommonAncestor {

    private Digraph graph;

    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {
        // space proportional to E + V
        this.graph = new Digraph(G);
    }

    private Map<Integer, Integer> pathToRoot(int n) {
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> map = new HashMap<>();
        queue.offer(n);
        map.put(n, 0);
        while (!queue.isEmpty()) {
            Integer cur = queue.poll();
            for (Integer neightbors : graph.adj(cur)) {
                if (!map.containsKey(neightbors)) {
                    map.put(neightbors, map.get(cur) + 1);
                }
                queue.offer(neightbors);
            }
        }
        return map;
    }

    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        if (v < 0 || v > graph.V())
            throw new IndexOutOfBoundsException();
        if (w < 0 || w > graph.V())
            throw new IndexOutOfBoundsException();

        Map<Integer, Integer> p1 = pathToRoot(v);
        Map<Integer, Integer> p2 = pathToRoot(w);

        int ret = Integer.MAX_VALUE;

        for (Map.Entry<Integer, Integer> entry : p1.entrySet()) {
            if (p2.containsKey(entry.getKey())) {
                ret = Math.min(ret, p2.get(entry.getKey()) + entry.getValue());
            }
        }

        return ret;
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        if (v < 0 || v > graph.V())
            throw new IndexOutOfBoundsException();
        if (w < 0 || w > graph.V())
            throw new IndexOutOfBoundsException();

        Map<Integer, Integer> p1 = pathToRoot(v);
        Map<Integer, Integer> p2 = pathToRoot(w);

        int ret = -1;
        int distance = Integer.MAX_VALUE;

        for (Map.Entry<Integer, Integer> entry : p1.entrySet()) {
            if (p2.containsKey(entry.getKey())) {
                int current = entry.getValue() + p2.get(entry.getKey());
                if (current < distance) {
                    ret = entry.getKey();
                    distance = current;
                }
            }
        }

        return ret;
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int length(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        int distance = Integer.MAX_VALUE;
        for (Integer a : subsetA) {
            for (Integer b : subsetB) {
                int current = length(a, b);
                distance = Math.min(distance, current);
            }
        }
        if (distance == Integer.MAX_VALUE) return -1;
        return distance;
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestor(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        int ret = -1;
        int distance = Integer.MAX_VALUE;
        for (Integer a : subsetA) {
            for (Integer b : subsetB) {
                int current = length(a, b);
                if (current < distance) {
                    distance = current;
                    ret = ancestor(a, b);
                }
            }
        }
        return ret;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);

        // path to root testing
        Map<Integer, Integer> map = sca.pathToRoot(11);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.print(entry.getKey());
            System.out.print(" ");
            System.out.print(entry.getValue());
            System.out.println();
        }

        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
