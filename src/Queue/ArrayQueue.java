package Queue;

import Array.Array;

/**
 * 用动态数组直接实现的队列在进行出队列的操作的时候时间复杂度是O(n)的
 * 因为走的是removeFirst的逻辑
 * @param <E>
 */

public class ArrayQueue<E> implements Queue<E> {
    private Array<E> array;

    public ArrayQueue(int capacity){
        array = new Array<E>(capacity);
    }
    public ArrayQueue(){
        array = new Array<E>();
    }

    @Override
    public int getSize() {
        return array.getSize();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public void offer(E e) {
        array.addLast(e);
    }

    @Override
    public E poll() {
        return array.removeFirst();
    }

    @Override
    public E peek() {
        return array.getFirst();
    }

    public int getCapacity(){
        return array.getCapacity();
    }
    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("Queue : front [ ");

        for(int i=0;i<array.getSize();i++){
            res.append(array.get(i));
            if(i!=array.getSize()-1){
                res.append(", ");
            }
        }
        res.append(" ] tail");
        return res.toString();
    }
}
