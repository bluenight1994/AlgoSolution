package com.hong.algo;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by GanHong on 3/19/17.
 */
public class BinarySearchDeluxe {

    // Returns the index of the first key in a[] that equals the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null)
            throw new NullPointerException();
        if (a.length == 0) return -1;
        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (comparator.compare(a[mid], key) < 0) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    // Returns the index of the last key in a[] that equals the search key, of -1 if no such key.
    public static <Key> int lastIndexof(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null)
            throw new NullPointerException();
        if (a.length == 0) return -1;
        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            int mid = (lo + hi) / 2 + 1;
            if (comparator.compare(key, a[mid]) < 0) {
                hi = mid - 1;
            } else {
                lo = mid;
            }
        }
        return hi;
    }

    // unit testing
    public static void main(String[] args) {
    }
}
