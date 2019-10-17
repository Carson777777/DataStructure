package Queue;

/**
 * 出队的时间复杂度是O(1)
 * front指向的是队列中第一个元素的下标
 * tail指向的是队列中最后一个元素的后一位,代表要插入的位置
 * front和tail有两个基本关系
 * 1,front==tail的时候表示队列为空
 * 2,(tail+1)%data.length == front的时候队列为满
 * 3,由于tail和front是循环的,根据条件1,数组中必须浪费一个元素的位置来实现循环
 * 4,那么要注意用户传入的capacity和我们new的data的length是差1的
 * @param <E>
 */
public class LoopQueue<E> implements Queue<E> {
    private E[] data;
    private int front,tail;
    private int size;//队列中元素的个数

    public LoopQueue(int capacity){
        data = (E[]) new Object[capacity+1];
        front = 0;
        tail = 0;
        size = 0;
    }
    public LoopQueue(){
        this(10);
    }
    @Override
    public int getSize() {
        return size;
    }

    public int getCapacity(){
        return data.length-1;
    }

    @Override
    public boolean isEmpty() {
        return front==tail;
    }

    @Override
    public void offer(E e) {
        if((tail+1) % data.length==front){
            resize(getCapacity()*2);
        }
        data[tail] = e;
        tail = (tail+1)%data.length;
        size++;
    }

    @Override
    public E poll() {
        if(isEmpty()){
            throw new IllegalArgumentException("队列为空无法poll值");
        }
        E res = data[front];
        data[front] = null;
        front = (front+1)%data.length;
        size--;
        if(size==getCapacity()/4 && getCapacity()/2!=0){
            resize(getCapacity()/2);
        }
        return res;
    }

    @Override
    public E peek() {
        if (isEmpty()){
            throw new IllegalArgumentException("队列为空无法peek值");
        }
        return data[front];
    }
    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append(String.format("Queue : 队列中有效位数:%d 队列的容量 %d \n",size,data.length-1));
        res.append("front [ ");
        for(int i = front;i != tail ;i = (i+1)%data.length){
            res.append(data[i]);
            if( (i+1)% data.length != tail){
                System.out.print("i="+i+"tail="+tail+" ");
                res.append(", ");
            }
        }
        res.append(" ]tail");
        return res.toString();
    }

    private void resize(int resize){
        E[] newdata = (E[]) new Object[resize+1];
        for(int i=0;i<size;i++){
            newdata[i] = data[(i+front)%data.length];
        }
        data = newdata;
        //后面这两项和动态数组中不同
        front = 0;
        tail = size;
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        LoopQueue<Integer> queue = new LoopQueue<Integer>();
        for (int i = 0; i < 10; i++) {
            queue.offer(i);
            System.out.println(queue);
            if( i % 3 == 2){//每隔三个数字进行一次操作
                queue.poll();
                System.out.println(queue);
            }
        }
    }
}
