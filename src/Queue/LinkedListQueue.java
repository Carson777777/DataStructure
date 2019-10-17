package Queue;
/**
 * 用链表实现Queue的时候,必须还有一个尾指针,头指针指向头,可以用来O(1)的插入删除查找
 * 尾指针只能O(1)插入,不能O(1)删除,还是得遍历,所以尾指针作为Queue的队尾,头指针作为Queue的队首
 * 因为我们不会对中间的元素进行操作了,所以不需要dummyHead了
 * java中的Queue是个接口,其实现非常多,其中LinkedList的实现就是循环链表实现的,通过一个dummyHead使链表成环
 * @param <E>
 */
public class LinkedListQueue<E> implements Queue<E> {
    private class Node {
        public E e;
        public Node next;

        public Node(E e,Node next) {
            this.e = e;
            this.next = next;
        }

        public Node(E e) {
            this(e, null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }
    private Node head,tail;
    private int size;

    public LinkedListQueue(){
        head = null;
        tail = null;
        size = 0;
    }
    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public void offer(E e) {
        if(tail==null){//tail为空的时候链表一定为空
            tail = new Node(e);
            head = tail;
        }else {
            tail.next = new Node(e);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public E poll() {
        if(isEmpty()){
            throw new IllegalArgumentException("队列为空不能poll值");
        }
        Node delNode = head;
        head = head.next;//移动head指针
        delNode.next = null;//把头断开
        if(head==null){//如果只有一个元素head=next=delNode,head指向next之后为空,那么此时要维护tail=null
            tail = null;//如果不维护 tail还是指向delNode
        }
        size--;
        return delNode.e;
    }

    @Override
    public E peek() {
        if(isEmpty()){
            throw new IllegalArgumentException("队列为空不能poll值");
        }
        return head.e;
    }
    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("LinkedListQueue : head  ");
        Node cur = head;
        while(cur!=null){
            res.append(cur.e + " -> ");
            cur = cur.next;
        }
        res.append(" NULL tail");
        return res.toString();
    }
    public static void main(String[] args) {
        LinkedListQueue<Integer> queue = new LinkedListQueue<Integer>();
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
