package com.hong.algo;

import edu.princeton.cs.algs4.Digraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GanHong on 3/22/17.
 */
public class WordNet {

    Map<Integer, String> dictionary = new HashMap<>();
    Map<String, List<Integer>> reverse_dictionary = new HashMap<>();
    ShortestCommonAncestor SCA;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) throws IOException {
        if (synsets == null || hypernyms == null)
            throw new NullPointerException();
        BufferedReader in = new BufferedReader(new FileReader(synsets));
        String line;
        int index = 0;
        while ((line = in.readLine()) != null) {
            String[] fields = line.split(",");
            String[] nouns = fields[1].split(" ");
            for (int i = 0; i < nouns.length; i++) {
                if (reverse_dictionary.containsKey(nouns[i])) {
                    List<Integer> cur = reverse_dictionary.get(nouns[i]);
                    cur.add(Integer.parseInt(fields[0]));
                } else {
                    List<Integer> nn = new ArrayList<>();
                    nn.add(Integer.parseInt(fields[0]));
                    reverse_dictionary.put(nouns[i], nn);
                }
            }
            dictionary.put(Integer.parseInt(fields[0]), fields[1]);
            index = Math.max(index, Integer.parseInt(fields[0]));
        }
        in.close();

        Digraph G = new Digraph(index + 1);
        in = new BufferedReader(new FileReader(hypernyms));
        while ((line = in.readLine()) != null) {
            String[] fields = line.split(",");
            int vertice = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                G.addEdge(vertice, Integer.parseInt(fields[i]));
            }
        }
        in.close();

        SCA = new ShortestCommonAncestor(G);
    }

    // all WordNet nouns
    public Iterable<String> nouns() {
        return reverse_dictionary.keySet();
    }

    // is the word a WordNet noun？
    public boolean isNoun(String word) {
        if (word == null)
            throw new NullPointerException();
        return reverse_dictionary.containsKey(word);
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defind below)
    public String sca(String noun1, String noun2) {
        if (noun1 == null || noun2 == null)
            throw new NullPointerException();
        int id = SCA.ancestor(reverse_dictionary.get(noun1), reverse_dictionary.get(noun2));
        return dictionary.get(id);
    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        if (noun1 == null || noun2 == null)
            throw new NullPointerException();
        return SCA.ancestor(reverse_dictionary.get(noun1), reverse_dictionary.get(noun2));
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}
