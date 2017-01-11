import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
/**
 * Created by GanHong on 10/23/16.
 */
public class Percolation {

    private int[][] grid;
    private WeightedQuickUnionUF UF;
    private int n;
    // this two fake cell to linked all cell on the top together.
    // link all bottom cell together.
    private int head;
    private int end;

    public Percolation(int n) {
        if(n <= 0) {
            throw new IllegalArgumentException(Integer.toString(n));
        }
        UF = new WeightedQuickUnionUF(n*n+2);
        this.n = n;
        grid = new int[n+1][n+1];
        head = 0;
        end = n*n+1;
    }

    private int index(int x, int y) {
        return (x-1)*n+y;
    }

    public void open(int row, int col) {
        grid[row][col] = 1;
        int cur = index(row, col);
        if(row == 1) UF.union(cur, head);
        if(row == n) UF.union(cur, end);
        if(row > 1 && isOpen(row-1, col)) UF.union(index(row-1, col), cur);
        if(col > 0 && isOpen(row, col-1)) UF.union(index(row, col-1), cur);
        if(row < n && isOpen(row+1, col)) UF.union(index(row+1, col), cur);
        if(col < n && isOpen(row, col+1)) UF.union(index(row, col+1), cur);
    }

    public boolean isOpen(int row, int col) {
        return grid[row][col] == 1;
    }

    public boolean isFull(int row, int col) {
        if( row > 0 && row <= n && col > 0 && col <= n ) {
            return UF.connected(head, index(row, col));
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public boolean percolates() {
        return UF.connected(head, end);
    }

    public static void main(String[] args) {

    }
}
