package HeapAndPriorityQueue;

public class PriorityQueue<E extends Comparable<E>> implements Queue<E> {

    private MaxHeap<E> maxHeap;

    public PriorityQueue(){
        maxHeap = new MaxHeap();
    }

    @Override
    public int getSize() {
        return maxHeap.getsize();
    }

    @Override
    public boolean isEmpty() {
        return maxHeap.isEmpty();
    }

    @Override
    public void offer(E e) {
        maxHeap.add(e);
    }

    @Override
    public E poll() {
        return maxHeap.extractMax();
    }

    @Override
    public E peek() {
        return maxHeap.findMax();
    }
}
