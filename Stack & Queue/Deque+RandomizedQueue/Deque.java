import java.util.Iterator;
import java.util.NoSuchElementException;

/* Princeton Algorithms programming assignment 2
 * implement a Deque
 * implement iterator
 */

public class Deque<Item> implements Iterable<Item> {

    private class ListNode {
        Item val;
        ListNode pre;
        ListNode next;

        public ListNode(Item val) {
            this.val = val;
        }
    }

    private ListNode head;
    private ListNode tail;
    private int size;

    public Deque() {
        head = new ListNode(null);
        tail = new ListNode(null);
        head.pre = null;
        head.next = tail;
        tail.pre = head;
        tail.next = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        ListNode node = new ListNode(item);
        ListNode tmp = head.next;
        head.next = node;
        node.next = tmp;
        tmp.pre = node;
        node.pre = head;
        size++;
    }

    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        ListNode node = new ListNode(item);
        ListNode tmp = tail.pre;
        tmp.next = node;
        node.next = tail;
        tail.pre = node;
        node.pre = tmp;
        size++;
    }

    public Item removeFirst() {
        if (size == 0) throw new NoSuchElementException();
        Item item = head.next.val;
        head = head.next;
        size--;
        return item;
    }

    public Item removeLast() {
        if (size == 0) throw new NoSuchElementException();
        Item item = tail.pre.val;
        tail = tail.pre;
        size--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>
    {
        private ListNode current = head.next;

        public boolean hasNext() {
            return current != tail;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (hasNext()) {
                Item item = current.val;
                current = current.next;
                return item;
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(4);
        System.out.println(deque.removeLast());
        deque.addFirst(6);
        System.out.println(deque.removeLast());
    }
}
