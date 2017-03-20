import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by GanHong on 10/31/16.
 */

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;

    public RandomizedQueue(){
        items = (Item[]) new Object[2];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize(int newSize) {
        Item[] tmp = (Item[]) new Object[newSize];
        for(int i=0; i<size; i++) {
            tmp[i] = items[i];
        }
        items = tmp;
    }

    public void enqueue(Item item) {
        if(item == null) throw new NullPointerException();
        if(size == items.length) {
            resize(size*2);
        }
        items[size] = item;
        size++;
    }

    public Item dequeue() {
        if(isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniform(0, size);
        Item item = items[index];
        if(index != size-1) {
            items[index] = items[size-1];
        }
        items[size-1] = null;
        size--;
        if(size>0 && size == items.length / 4)
            resize(items.length/2);
        return item;
    }

    public Item sample() {
        if(isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniform(size);
        Item item = items[index];
        return item;
    }

    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item>
    {
        private int index = 0;
        private int itSize = size;
        private Item[] tmp = (Item[]) new Object[itSize];

        public boolean hasNext() {
            return index < itSize;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if(itSize == 0) throw new NoSuchElementException();
            int random = StdRandom.uniform(itSize);
            Item item = tmp[random];
            if(random != itSize-1) {
                tmp[random] = tmp[itSize-1];
            }
            tmp[itSize-1] = null;
            itSize--;
            return item;
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        for(int i=0; i<100; i++) {
            rq.enqueue(i);
        }
        for(int k=0; k<10; k++) {
            System.out.println(rq.sample());
        }
    }
}
