package com.hong.algo;


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Created by GanHong on 3/19/17.
 */
public class Autocomplete {

    private Term[] terms;

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) throw new NullPointerException();
        for (Term tmp : terms) {
            if (tmp == null) {
                throw new NullPointerException();
            }
        }
        this.terms = terms;
        Arrays.sort(this.terms);
    }

    // Returns all terms that start with the given prefix, in descending order of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null) throw new NullPointerException();
        Term tmp = new Term(prefix, 0);
        int start = BinarySearchDeluxe.firstIndexOf(terms, tmp, Term.byPrefixOrder(prefix.length()));
        int end = BinarySearchDeluxe.lastIndexof(terms, tmp, Term.byPrefixOrder(prefix.length()));
        Term[] matches = new Term[end - start + 1];
        int index = 0;
        for (int i = start; i <= end; i++) {
            matches[index++] = terms[i];
        }
        Arrays.sort(matches, Term.byReverseWeightOrder());
        return matches;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) throw new NullPointerException();
        Term tmp = new Term(prefix, 0);
        int start = BinarySearchDeluxe.firstIndexOf(terms, tmp, Term.byPrefixOrder(prefix.length()));
        int end = BinarySearchDeluxe.lastIndexof(terms, tmp, Term.byPrefixOrder(prefix.length()));
        return end - start + 1;
    }

    // unit testing (required)
    public static void main(String[] args) {
        String filename = "wiktionary.txt";
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }

        // read in queries from standard input and print out the top k matching terms
        System.out.println("read data finished");
        int k = 5;
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }

    }
}
