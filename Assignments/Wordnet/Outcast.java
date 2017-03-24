package com.hong.algo;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.io.IOException;

/**
 * Created by GanHong on 3/24/17.
 */
public class Outcast {

    WordNet wn;

    public Outcast(WordNet wordnet) {
        wn = wordnet;
    }

    public String outcast(String[] nouns) {
        int id = -1;
        int distance = 0;
        for (int i = 0; i < nouns.length ; i++) {
            int tmp = 0;
            for (int j = 0; j < nouns.length && j != i; j++) {
                tmp += wn.distance(nouns[i], nouns[j]);
            }
            if (tmp > distance) {
                id = i;
                distance = tmp;
            }
        }
        return nouns[id];
    }

    public static void main(String[] args) throws IOException {
        WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
//        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        String[] outs = {"outcast5.txt", "outcast8.txt", "outcast11.txt"};
        for (int t = 0; t < outs.length; t++) {
            In in = new In(outs[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(outs[t] + ": " + outcast.outcast(nouns));
        }
    }
}
