### LRU Cache

---

Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: `get` and `set`.

`get(key)` - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
`set(key, value)` - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.

---

> Hash table and linked list implementation of the Map interface, with predictable iteration order. This implementation differs from HashMap in that it maintains a doubly-linked list running through all of its entries. This linked list defines the iteration ordering, which is normally the order in which keys were inserted into the map (*insertion-order*). Note that insertion order is not affected if a key is *re-inserted* into the map. (A key k is reinserted into a map m if m.put(k, v) is invoked whenm.containsKey(k) would return true immediately prior to the invocation.)

```java
import java.util.LinkedHashMap;

public class LRUCache {
    
    private LinkedHashMap<Integer, Integer> map;
    private final int CAPACITY;
    
    public LRUCache(int capacity) {
        CAPACITY = capacity;
        map = new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true){
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > CAPACITY;
            }
        };
    }
        
    public int get(int key) {
        return map.getOrDefault(key, -1);
    }
    
    public void set(int key, int value) {
        map.put(key, value);
    }
}
```

This is straight implementation. but interviewer would not like it. LRU Cache can be implemented through HashTable + double linked list

```java
class Node {
  int key;
  int value;
  Node pre;
  Node next;
  
  public Node(int key, int value) {
    this.key = key;
    this.value = value;
  }
}

public class LRUCache {
  Map<Integer, Node> map;
  int capacity;
  int count;
  int head, tail;
  
  public LRUCache(int capacity) {
    this.capacity = capacity;
    count = 0;
   	head = new Node(0,0);
    tail = new Node(0,0);
    head.pre = null;
    head.next = tail;
    tail.pre = head;
    tail.next = null;
  }
  
  public void delete(Node node) {
    node.pre.next = node.next;
    node.next.pre = node.pre;
  }
  
  public void addToHead(Node node) {
    node.next = head.next;
    node.next.pre = node;
    node.pre = head;
    head.next= node;
  }
  
  public int get(int key) {
    if(map.containsKey(key)) {
      Node node = map.get(key);
      int result = node.value;
      delete(node);
      addToHead(node);
      return result;
    }
    return -1;
  }
  
  public void set(int key, int value) {
    if(map.containsKey(key)) {
      Node node = map.get(key);
      node.value = value;
      delete(node);
      addToHead(node);
    } else {
      Node node = new Node(key, value);
      map.put(key, node);
      if(count < capacity) {
        count++;
      } else {
        map.remove(tail.pre.key);
        delete(tail.pre);
      }
      addToHead(node);
    }
  }
}
```



