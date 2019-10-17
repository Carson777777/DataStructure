package Queue;

import Array.Array;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int opCount = 100000;
        ArrayQueue<Integer> arrayQueue  = new ArrayQueue<Integer>();
        LoopQueue<Integer> loopQueue = new LoopQueue<Integer>();
        double time1 = testQueue(arrayQueue,opCount);
        double time2 = testQueue(loopQueue,opCount);
        System.out.println("数组队列时间为"+time1);
        System.out.println("循环队列时间为"+time2);
    }
    public static double testQueue(Queue<Integer>queue,int opCount){
        long startTime = System.nanoTime();
        Random random = new Random();
        for (int i=0;i<opCount;i++){
            queue.offer(random.nextInt(Integer.MAX_VALUE));
        }
        for (int i=0;i<opCount;i++){
            queue.poll();
        }
        long endTime  = System.nanoTime();
        return (endTime-startTime)/1000000000.0;
    }
}
