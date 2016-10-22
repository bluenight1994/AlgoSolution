### Rotated Array

---

A rotated array rotate the n-element vector left by i position. This has a lot of application. such as swap adjacent block of memory of unequal size. just as change the sequence of paragraph in word.

1.  how to rotate an array effectively.

   > first reverse the first half, next reverse the second half. next reverse the whole array

   [1,2,3,4,5,6,7,8,9,10] for example rotate the array by 4, the array become [5,6,7,8,9,10,1,,2,3,4]

   first reverse the first part (4,3,2,1,5,6,7,8,9,10), then reverse the second half (4,3,2,1,10,9,8,7,6,5)

   then reverse the whole part (5,6,7,8,9,10,1,2,3,4)

2. rotated sorted array

   (1,2,3,4,5,6,7,8,9) - > (4,5,6,7,8,9,1,2,3) the array is separated into two parts. each part is sorted. and the minimum element is in the middle place.

3. How to find that minimum value in a rotated sorted array?

   ```java
   public int findMin(int[] nums) {
     int lo = 0, hi = nums.length-1;
     while(lo < hi) {
       int mid = (lo+hi)/2;
       if(nums[mid] > nums[hi]) lo = mid + 1;
       else hi = mid;
     }
     return nums[lo];
   }
   // this solution does not consider the situation when there are duplicates
   // consider that follow case
   // 444444444444934
   // when nums[mid] == nums[hi]
   // direct cut hi to mid is wrong. you miss the 3, in this case, you should  
   // decrease the hi very carefully
   public int findMin(int[] nums) {
     int lo = 0, hi = nums.length-1;
     while(lo < hi) {
       int mid = (lo+hi)/2;
       if(nums[mid] > nums[hi]) lo = mid + 1;
       else if (nums[mid] < nums[hi]) hi = mid;
       else hi--; // nums[mid] == nums[hi];
     }
     return nums[lo];
   }
   ```

4. How to search  in this rotated sorted array?

   Remember when we want to search in a sorted array, we can use binary search. it can find a target in O(log(n)) time. But how do we use binary search in rotated sorted array?

   We definitely need to use the helper method we implemented in Q3

   Now what if i give you a sorted array, and i told you that how it is rotated from the original array. we surely can recover the original array. and perform the binary search to find target.

   So now if we are given a rotated, sorted array, we can first find the minimum element in the array. this is actually the lo pointer in the original array.

   ```java
   public int search (int[] nums, int target) {
     int lo = 0, hi = nums.length-1;
     int offset = findMin(nums);
     int lo = 0, hi = nums.length-1;
     while(lo <= hi) {
       int mid = (lo + hi) / 2;
       int rmid = (mid + offset) % nums.length;
       if(nums[rmid] == target) return rmid;
       else if(nums[rmid] > target) lo = mid + 1;
       else hi = mid - 1;
     }
     return -1;
   }
   ```

   if we consider the array can contains duplicated numbers….

   ​

    

   ​