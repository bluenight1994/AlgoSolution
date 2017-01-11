/**
 * Created by GanHong on 10/01/2017.
 */

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final int[][] blocks;
    private final int n;

    public Board(int[][] blocks) {
        this.n = blocks.length;
        this.blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        int hamm = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) continue;
                int target = (i * n + j + 1) % (n * n);
                if (blocks[i][j] != target) hamm++;
            }
        }
        return hamm;
    }

    public int manhattan() {
        int man = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) continue;
                int dx = (blocks[i][j] - 1) / n;
                int dy = (blocks[i][j] - 1) % n;
                man += Math.abs(i - dx) + Math.abs(j - dy);
            }
        }
        return man;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    private Board swap(int x1, int y1, int x2, int y2) {
        int[][] cp = copy(blocks);
        int temp = cp[x1][y1];
        cp[x1][y1] = cp[x2][y2];
        cp[x2][y2] = temp;
        return new Board(cp);
    }

    public Board twin() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ( j != n-1 && blocks[i][j] != 0 && blocks[i][j+1] != 0) {
                    return swap(i,j,i,j+1);
                }
            }
        }
        return new Board(new int[0][0]);
    }

    private static int[][] copy(int[][] a) {
        int[][] cp = new int[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                cp[i][j] = a[i][j];
            }
        }
        return cp;
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null || !(y instanceof Board) || ((Board)y).n != this.n) return false;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (((Board)y).blocks[i][j] != this.blocks[i][j]) return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        List<Board> neighbors  = new ArrayList<>();
        int sx = -1, sy = -1;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.blocks[i][j] == 0) {
                    sx = i;
                    sy = j;
                    break;
                }
            }
        }
        if (sx > 0)             neighbors.add(swap(sx,sy,sx-1,sy));
        if (sx < this.n - 1)    neighbors.add(swap(sx,sy,sx+1,sy));
        if (sy > 0)             neighbors.add(swap(sx,sy,sx,sy-1));
        if (sy < this.n - 1)    neighbors.add(swap(sx,sy,sx,sy+1));
        return neighbors;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.n + "\n");
        for (int i = 0; i < this.n ; i++) {
            for (int j = 0; j<this.n; j++) {
                str.append(this.blocks[i][j]);
                str.append(" ");
            }
            str.append("\n");
        }
        return str.toString();
    }

    public static void main(String[] args) {
        int[][] input = new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        int[][] input2 = new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        int[][] input3 = new int[][]{{0, 5, 7}, {1, 8, 4}, {3, 2, 6}};

        Board testBoard = new Board(input);
        Board testBoard2 = new Board(input2);
        Board testBoard3 = new Board(input3);

        StdOut.println(testBoard2.hamming());
        StdOut.println(testBoard2.manhattan());

        Iterable<Board> result = testBoard2.neighbors();

        for (Board b : result) {
            StdOut.println(b.toString());
        }
    }
}
