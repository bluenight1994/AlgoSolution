package com.hong.algo;

/**
 * Created by GanHong on 10/22/16.
 */
public class UnionFind {

    int[] id;
    int[] sz;

    public UnionFind(int n) {
        int[] id = new int[n];
        int[] sz = new int[n];
        for(int i=0; i<n; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    public boolean find(int p, int q) {
        return root(p) == root(q);
    }

    private int root(int i) {
        for( ; i != id[i]; i = id[i]) {
            id[i] = id[id[i]];
        }
        return i;
    }

    public void unite(int p, int q) {
        int i = root(p);
        int j = root(q);
        if(sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
    }


}
