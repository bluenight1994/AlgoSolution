import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by GanHong on 11/01/2017.
 */
public class Solver {

    private MinPQ<Node> pq;
    private MinPQ<Node> pq_t;
    private Node finalNode;
    private boolean solvable;

    private class Node {
        private Board board;
        private Node prevNode;
        private int moves;

        public Node(Board board, Node prevNode) {
            this.board = board;
            this.prevNode = prevNode;
            if (prevNode == null) {
                moves = 0;
            } else {
                moves = prevNode.moves + 1;
            }
        }
    }

    private Comparator<Node> nodeComparator = new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.board.manhattan() + o1.moves - o2.board.manhattan() - o2.moves;
        }
    };

    public Solver(Board initial) {
        pq = new MinPQ<>(nodeComparator);
        pq_t = new MinPQ<>(nodeComparator);

        solvable = false;

        Node node = new Node(initial, null);
        Node node_t = new Node(initial.twin(), null);

        pq.insert(node);
        pq_t.insert(node_t);

        node = pq.delMin();
        node_t = pq_t.delMin();

        while (!node.board.isGoal() && !node_t.board.isGoal()) {
            for (Board b : node.board.neighbors()) {
                if (node.prevNode == null || !b.equals(node.prevNode.board)) {
                    Node neighbor = new Node(b, node);
                    pq.insert(neighbor);
                }
            }

            for (Board b_t : node_t.board.neighbors()) {
                if (node_t.prevNode == null || !b_t.equals(node_t.prevNode.board)) {
                    Node neighbor = new Node(b_t, node_t);
                    pq_t.insert(neighbor);
                }
            }

            node = pq.delMin();
            node_t = pq_t.delMin();

        }

        if (node.board.isGoal()) {
            solvable = true;
            finalNode = node;
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        // min number of moves to solve initial board; -1 if unsolvable
        if (!solvable) return -1;
        return finalNode.moves;
    }

    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
        if (!solvable) return null;

        List<Board> sequence = new ArrayList<>();
        Node cur = finalNode;
        sequence.add(cur.board);
        while (cur.prevNode != null) {
            sequence.add(cur.prevNode.board);
            cur = cur.prevNode;
        }
        Collections.reverse(sequence);
        return sequence;
    }

    public static void main(String[] args) {

        // create initial board from file

        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
