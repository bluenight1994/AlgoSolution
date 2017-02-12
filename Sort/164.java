/*  
    Use Bucket Sort to find the maximum gap in an unsorted array.
    max: the max value in the array
    min: the min value in the array
    the total range is max - min
    the maximum gap is at least (max-min) / (nums.length - 1), even distributed
    the idea of this solution is to use bucket to divide the total range.
    each bucket maintain the max num and min num fall into this range.

*/
public class Solution{
    
    static class Bucket {
        boolean used = false;
        int minVal = Integer.MAX_VALUE;
        int maxVal = Integer.MIN_VALUE;
    }
    
    public int maximumGap(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }   
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            max = Math.max(max, num);
            min = Math.min(min, num);
        }
        
        int bucketSize = Math.max(1, (max-min) / (nums.length - 1));
        int bucketNum = (max - min) / bucketSize + 1;
        List<Bucket> buckets = new ArrayList<>();
        for (int i = 0; i < bucketNum; i++) {
            buckets.add(new Bucket());
        }
        for (Integer num : nums) {
            int id = (num - min) / bucketSize;
            buckets.get(id).used = true;
            buckets.get(id).minVal = Math.min(num, buckets.get(id).minVal);
            buckets.get(id).maxVal = Math.max(num, buckets.get(id).maxVal);
        }
        
        int prevBucketMax = min, maxGap = 0;
        for(Bucket b : buckets) {
            if (!b.used) continue;
            maxGap = Math.max(maxGap, b.minVal - prevBucketMax);
            prevBucketMax = b.maxVal;
        }
        return maxGap;
    }
}