package HeapAndPriorityQueue;
/// 347. Top K Frequent Elements
/// https://leetcode.com/problems/top-k-frequent-elements/description/
/// 我们去解决一个Top K的问题的时候,从N个数中取出优先级为M的数
/// 如果使用高级排序时间复杂度为O(nlogn)但是如果我们维护一个长度为M的优先队列,可以把复杂度降低到O(nlogM)在m和n的差别很大的时候复杂度降低很多

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class Solution {
    private class Freq implements Comparable<Freq>{
        int e,freq;

        public Freq(int e,int freq){
            this.e = e;
            this.freq = freq;
        }
        //频次越低 优先级越高
        @Override
        public int compareTo(Freq another) {
            if(this.freq<another.freq){
                return 1;//返回正数表示优先级高
            }else if (this.freq>another.freq){
                return -1;
            }else{
                return 0;
            }
        }
    }
    public List<Integer> topKFrequent(int [] nums,int k){
        TreeMap<Integer,Integer> map = new TreeMap<>();

        for(int i=0;i<nums.length;i++){
            if (map.containsKey(nums[i])){
                map.put(nums[i],map.get(nums[i])+1);
            }else{
                map.put(nums[i],1);
            }
        }

        PriorityQueue<Freq> priorityQueue = new PriorityQueue<>();
        for(int key:map.keySet()){
            if(priorityQueue.getSize()<k){
                priorityQueue.offer(new Freq(key,map.get(key)));
            }else if (map.get(key)>priorityQueue.peek().freq){
                priorityQueue.poll();
                priorityQueue.offer(new Freq(key,map.get(key)));
            }
        }

        LinkedList<Integer> linkedList= new LinkedList<Integer>();
        while(!priorityQueue.isEmpty()){
            linkedList.add(priorityQueue.poll().e);
        }
        return linkedList;
    }
}
