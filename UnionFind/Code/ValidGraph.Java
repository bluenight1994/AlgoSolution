public class Solution {
    
    class UnionFind {
        int[] id;
        int[] sz;
        int n;
        
        public UnionFind(int n) {
            id = new int[n];
            sz = new int[n];
            for (int i = 0; i < n; i++) {
                id[i] = i;
                sz[i] = 1;
            }
            this.n = n;
        }
        
        public boolean find(int p, int q) {
            return root(p) == root(q);
        }
        
        private int root(int i) {
            for(; i != id[i]; i = id[i]) {
                id[i] = id[id[i]];
            }
            return i;
        }
        
        public void union(int p, int q) {
            n--;
            int i = root(p);
            int j = root(q);
            if (sz[i] > sz[j]) {
                id[j] = i;
                sz[i] += sz[j];
            } else {
                id[i] = j;
                sz[j] += sz[i];
            }
        }
    }
    
    public boolean validTree(int n, int[][] edges) {
        UnionFind uf = new UnionFind(n);
        for(int[] edge : edges) {
            int x = edge[0];
            int y = edge[1];
            if (uf.find(x, y)) return false;
            else uf.union(x, y);
        }
        System.out.println(uf.n);
        return uf.n == 1;
    }
}