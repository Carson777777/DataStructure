package HeapAndPriorityQueue;

import java.util.*;
import java.util.PriorityQueue;

public class Solution2 {
    private class Freq{
        int e,freq;

        public Freq(int e,int freq){
            this.e = e;
            this.freq = freq;
        }
    }
    private class FreqComparator implements Comparator<Freq>{
        @Override
        public int compare(Freq o1, Freq o2) {
            return o1.freq-o2.freq;
        }
    }
    public List<Integer> topKFrequent(int [] nums, int k){

        TreeMap<Integer,Integer> map = new TreeMap<>();

        for(int i=0;i<nums.length;i++){
            if (map.containsKey(nums[i])){
                map.put(nums[i],map.get(nums[i])+1);
            }else{
                map.put(nums[i],1);
            }
        }

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(
                (a,b) -> map.get(a)-map.get(b)
        );
        for(int key:map.keySet()){
            if(priorityQueue.size()<k){
                priorityQueue.offer(key);
            }else if (map.get(key)>map.get(priorityQueue.peek())){
                priorityQueue.poll();
                priorityQueue.offer(key);
            }
        }

        LinkedList<Integer> linkedList= new LinkedList<Integer>();
        while(!priorityQueue.isEmpty()){
            linkedList.add(priorityQueue.poll());
        }
        return linkedList;
    }
}
