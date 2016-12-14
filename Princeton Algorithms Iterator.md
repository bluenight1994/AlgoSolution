### Princeton Algorithms Iterator

---

```java
public interface Iterable<Item>
{
  Iterator<Item> iterator();
}

public interface Iterator<Item>
{
  boolean hasNext();
  Item next();
  void remove();
}
```

 ![Screen Shot 2016-10-31 at 12.34.34 AM](/Users/GanHong/Desktop/Screen Shot 2016-10-31 at 12.34.34 AM.png)

