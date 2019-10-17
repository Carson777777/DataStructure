package SegmentTree;
//LeetCode 307 数组中的数是可以update的,用线段树比用动态规划的思想要好
//如果用动态规划的思想,每一次update之后要重新生成一次sum数组,那么update的时间复杂度是O(n),会超时
//https://leetcode-cn.com/problems/range-sum-query-mutable/

public class NumsArray2 {
    class NumArray {
        SegmentTree<Integer> segmentTree;
        public NumArray(int[] nums) {
            if(nums.length>0){
                Integer [] data = new Integer[nums.length];
                for(int i=0;i<nums.length;i++){
                    data[i] = nums[i];
                }
                segmentTree = new SegmentTree<Integer>(data,(a,b)->a+b);
            }

        }

        public void update(int i, int val) {
            if (segmentTree==null){
                throw new IllegalArgumentException("");
            }
            segmentTree.set(i,val);
        }

        public int sumRange(int i, int j) {
            if (segmentTree==null){
                throw new IllegalArgumentException("");
            }
            return segmentTree.query(i,j);
        }
    }
}
