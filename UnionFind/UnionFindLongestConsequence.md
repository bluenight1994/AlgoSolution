### find the longest consecutive sequence

---

Given an unsorted array of integers, find the length of the longest consecutive elements sequence.

For example,
Given `[100, 4, 200, 1, 3, 2]`,
The longest consecutive elements sequence is `[1, 2, 3, 4]`. Return its length: `4`.

Use union found. also use hash map to map the index with the number. 

weighted + path compression

```java
public class Solution {
    
    int[] sz;
    int[] id;
    Map<Integer, Integer> map;
    
    public int longestConsecutive(int[] nums) {
        map = new HashMap<>();
        sz = new int[nums.length];
        id = new int[nums.length];
        for(int i=0; i<nums.length; i++) {
            if(!map.containsKey(nums[i])) {
                map.put(nums[i],i);
                id[i] = i;
                sz[i] = 1;
                if(map.containsKey(nums[i]-1)) {
                    unite(i, map.get(nums[i]-1));
                }
                if(map.containsKey(nums[i]+1)) {
                    unite(i, map.get(nums[i]+1));
                }
            }
        }
        int maxValue = Integer.MIN_VALUE;
        for(int i=0; i<sz.length; i++) {
            maxValue = Math.max(maxValue, sz[i]);
        }
        return maxValue;
    }
    
    public void unite(int a, int b) {
        int p = root(a);
        int q = root(b);
        if(sz[p] < sz[q]) {
            id[p] = q;
            sz[q] += sz[p];
        } else {
            id[q] = p;
            sz[p] += sz[q];
        }
    }
    
    private int root(int i) {
        for(; i!=id[i]; i=id[i]) {
            id[i] = id[id[i]];
        }
        return i;
    }
}
```

