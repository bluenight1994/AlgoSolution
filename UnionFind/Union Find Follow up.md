Union Find Follow up

1. **Social network connectivity**

   > Given a social network containing n members and a log file containing m timestamps at which times pairs of members formed friendships, design an algorithm to determine the earliest time at which all members are connected (i.e., every member is a friend of a friend of a friend ... of a friend). Assume that the log file is sorted by timestamp and that friendship is an equivalence relation. The running time of your algorithm should be mlogn or better and use extra space proportional to n.

   initial the data type, and add property named as count  = n at the beginning. each time when we try to union two object. count--; when count == 1then is found

   O(MlogN + M) = O(M(logN + 1)) = O(MlogN)

2. **Union-find with specific canonical element.**

   > Add a method 𝚏𝚒𝚗𝚍() to the union-find data type so that 𝚏𝚒𝚗𝚍(𝚒) returns the largest element in the connected component containing i. The operations, 𝚞𝚗𝚒𝚘𝚗(), 𝚌𝚘𝚗𝚗𝚎𝚌𝚝𝚎𝚍(), and 𝚏𝚒𝚗𝚍() should all take logarithmic time or better.

   in constructor initialize another arr called maximum to track each maximum object connected with i. in union operation, compare maximum val of two id.

   ```java
   //reference : https://gist.github.com/jingz8804/9026369
   public void union(int p, int q){
         int rootP = root(p);
         int rootQ = root(q);
         
         if (rootP == rootQ) return;
         
         if (sz[rootP] >= sz[rootQ]){
             sz[rootP] += sz[rootQ];
             id[rootQ] = rootP;
             if (maximum[rootQ] > maximum[rootP]){
                 maximum[rootP] = maximum[rootQ];
             }
         }else{
             sz[rootQ] += sz[rootP];
             id[rootP] = rootQ;
             if (maximum[rootP] > maximum[rootQ]){
                 maximum[rootQ] = maximum[rootP];
             }
         }
         count--;
       }
   ```

3. **Successor with delete**

   Given a set of N integers S={0,1,...,N−1} and a sequence of requests of the following form:

   - Remove x from S
   - Find the *successor* of x: the smallest y in S such that y≥x.

   design a data type so that all operations (except construction) should take logarithmic time or better.

   boolean[] to record current is deleted, when it is deleted, if its prev and next is also deleted, unite them. in successor, if x is not deleted, return x, else, return ufmax.find(x)+1