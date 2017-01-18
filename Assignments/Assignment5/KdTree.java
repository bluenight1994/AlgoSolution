import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;

/**
 * Created by GanHong on 13/01/2017.
 */
public class KdTree {

    private static class KdNode {

        private KdNode left;
        private KdNode right;
        private final boolean isVertical;
        private double x;
        private double y;

        public KdNode(final double x, final double y, final boolean isVertical) {
            this.x = x;
            this.y = y;
            this.isVertical = isVertical;
            this.left = null;
            this.right = null;
        }
    }

    private static final RectHV CONTAINER = new RectHV(0, 0, 1, 1);
    private KdNode root;
    private int size;

    public KdTree() {
        this.size = 0;
        this.root = null;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void insert(Point2D p) {
        this.root = insert(this.root, p, true);
    }

    private KdNode insert(final KdNode node, final Point2D p, final boolean isVertical) {
        if (node == null) {
            this.size++;
            return new KdNode(p.x(), p.y(), isVertical);
        }

        if (node.x == p.x() && node.y == p.y()) {
            return node;
        }

        if (node.isVertical && p.x() < node.x || !node.isVertical
                && p.y() < node.y) {
            node.left = insert(node.left, p, !node.isVertical);
        } else {
            node.right = insert(node.right, p, !node.isVertical);
        }
        return node;
    }

    public boolean contains(Point2D p) {
        return contains(root, p.x(), p.y());
    }

    private boolean contains(KdNode node, double x, double y) {
        if (root == null) return false;
        if (node.x == x && node.y == y) return true;
        if (node.isVertical && x < node.x || !node.isVertical && y < node.y) {
            return contains(node.left, x ,y);
        } else {
            return contains(node.right, x, y);
        }
    }

    public void draw() {
        StdDraw.setScale(0, 1);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        CONTAINER.draw();

        draw(root, CONTAINER);
    }

    private void draw(final KdNode node, final RectHV rect) {
        if (node == null) {
            return;
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        new Point2D(node.x, node.y).draw();

        Point2D min, max;
        if (node.isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            min = new Point2D(node.x, rect.ymin());
            max = new Point2D(node.x, rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            min = new Point2D(rect.xmin(), node.y);
            max = new Point2D(rect.xmax(), node.y);
        }

        StdDraw.setPenRadius();
        min.drawTo(max);

        draw(node.left, leftRect(rect, node));
        draw(node.right, rightRect(rect, node));
    }

    private RectHV leftRect(final RectHV rect, final KdNode node) {
        if (node.isVertical) {
            return new RectHV(rect.xmin(), rect.ymin(), node.x, rect.ymax());
        } else {
            return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.y);
        }
    }

    private RectHV rightRect(final RectHV rect, final KdNode node) {
        if (node.isVertical) {
            return new RectHV(node.x, rect.ymin(), rect.xmax(), rect.ymax());
        } else {
            return new RectHV(rect.xmin(), node.y, rect.xmax(), rect.ymax());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        final TreeSet<Point2D> set = new TreeSet<>();
        range(root, CONTAINER, rect, set);
        return set;
    }

    private void range(final KdNode node, final RectHV container, final RectHV border, final TreeSet<Point2D> set) {
        if (node == null) return;
        if (border.intersects(container)) {
            final Point2D point = new Point2D(node.x, node.y);
            if (border.contains(point)) {
                set.add(point);
            }
            range(node.left, leftRect(container, node), border, set);
            range(node.right, rightRect(container, node), border, set);
        }
    }

    /***************************************************************************************************************
     * Nearest neighbor search. To find a closest point to a given query point,                                    *
     * start at the root and recursively search in both subtrees using the following pruning rule:                 *
     * if the closest point discovered so far is closer than the distance between the query point                  *
     * and the rectangle corresponding to a node, there is no need to explore that node (or its subtrees).         *
     * That is, a node is searched only if it might contain a point that is closer than the best one found so far. *
     * The effectiveness of the pruning rule depends on quickly finding a nearby point.                            *
     * To do this, organize your recursive method so that when there are two possible subtrees to go down,         *
     * you always choose the subtree that is on the same side of the splitting line as the query point             *
     * as the first subtree to explore—the closest point found while exploring the first subtree may               *
     * enable pruning of the second subtree.                                                                       *
     * *************************************************************************************************************
     */

    public Point2D nearest(Point2D p) {
        return nearest(root, CONTAINER, p, null);
    }

    private Point2D nearest(final KdNode node, final RectHV rect, final Point2D p, final Point2D candidate) {
        if (node == null) return candidate;
        double currentDistance = 0.0;
        double potentialDistance = 0.0;
        RectHV left = null;
        RectHV right = null;
        Point2D nearest = candidate;

        if (candidate != null) {
            currentDistance = p.distanceSquaredTo(candidate);
            potentialDistance = rect.distanceSquaredTo(p);
        }

        if (currentDistance > potentialDistance || candidate == null) {
            Point2D current = new Point2D(node.x, node.y);
            if (nearest == null || p.distanceSquaredTo(current) < currentDistance) {
                nearest = current;
            }
            if (node.isVertical) {
                left = new RectHV(rect.xmin(), rect.ymin(), node.x, rect.ymax());
                right = new RectHV(node.x, rect.ymin(), rect.xmax(), rect.ymax());
                if (p.x() < node.x) {
                    nearest = nearest(node.left, left, p, nearest);
                    nearest = nearest(node.right, right, p, nearest);
                } else {
                    nearest = nearest(node.right, right, p, nearest);
                    nearest = nearest(node.left, left, p, nearest);
                }
            } else {
                left = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.y);
                right = new RectHV(rect.xmin(), node.y, rect.xmax(), rect.ymax());
                if (p.y() < node.y) {
                    nearest = nearest(node.left, left, p, nearest);
                    nearest = nearest(node.right, right, p, nearest);
                } else {
                    nearest = nearest(node.right, right, p, nearest);
                    nearest = nearest(node.left, left, p, nearest);
                }
            }
        }

        return nearest;
    }
}
