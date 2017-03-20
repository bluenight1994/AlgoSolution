package com.hong.algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by GanHong on 3/19/17.
 */
public class Term implements Comparable<Term> {

    private String query;
    private long weight;

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null) throw new NullPointerException();
        if (weight < 0) throw new IllegalArgumentException();
        this.query = query;
        this.weight = weight;
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        Comparator<Term> comparator = new Comparator<Term>() {
            @Override
            public int compare(Term o1, Term o2) {
                return (int)(o2.weight - o1.weight);
            }
        };
        return comparator;
    }

    // Compares the two terms in lexicographic order but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(final int r) {
        if (r < 0) throw new IllegalArgumentException();
        return  new Comparator<Term>() {
            @Override
            public int compare(Term o1, Term o2) {
                int l1 = o1.query.length();
                int l2 = o2.query.length();
                int k = Math.min(l2, Math.min(l1, r));
                String sub1 = o1.query.substring(0, k);
                String sub2 = o2.query.substring(0, k);
                return sub1.compareTo(sub2);
            }
        };
    }

    // Compares the two terms in lexicographic order by query
    public int compareTo(Term that) {
        return query.compareTo(that.query);
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return weight + "\t" + query;
    }

    public static void main(String[] args) {
        Term t1 = new Term("azcei", 12);
        Term t2 = new Term("abct", 14);
        ArrayList<Term> arr = new ArrayList<>();
        arr.add(t1);
        arr.add(t2);
        Collections.sort(arr, byPrefixOrder(2));
        for (Term tmp : arr) {
            System.out.println(tmp.query);
        }
    }
}
