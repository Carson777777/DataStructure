package SegmentTree;
//LeetCode 303 数组内的数不可变,那么我们不用线段树,用这个动态规划的思想是更好的
public class NumsArray {
    class NumArray {
        int [] sum;//sum[i]保存的是[0,i-1]的和 其中sum[0]初始化为0,代表没有和
        public NumArray(int[] nums) {
            sum = new int [nums.length+1];
            sum[0] = 0;
            for(int i=1;i<nums.length;i++){
                sum[i] = sum[i-1] + nums[i-1];
            }
        }

        public int sumRange(int i, int j) {
            return sum[j+1]-sum[i];
        }
    }
}
