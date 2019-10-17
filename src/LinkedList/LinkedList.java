package LinkedList;

/**
 * 使用虚拟头节点的list
 * 这个结构除了对链表头的删除,和链表头的插入还有链表头的peek是O(1)的以外
 * 其他的增删改查都是O(n)的
 * 根据上面这三个为O(1)的,我们可以知道,用链表实现栈是非常合适的,因为栈只能操作顶端
 * @param <E>
 */
public class LinkedList<E> {
    private class Node {
        public E e;
        public Node next;

        public Node(E e, Node next) {
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

    //链表中的数据结构
    private Node dummyHead;//链表的头结点
    private int size;//链表中节点的个数

    public LinkedList() {
        dummyHead = new Node(null, null);//初始化的时候就存在一个节点的
        size = 0;//size仍然初始化为0
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    //在头部插入数据
    public void addFirst(E e) {
        add(0, e);
    }

    //在任意的"索引"index的位置上插入数据,关键在于找到index的前一个节点

    /**
     * 有虚拟头结点的add,head是有前一个节点的 此时index是否等于0无所谓
     *
     * @param index 索引
     * @param e     value
     */
    public void add(int index, E e) {
        if (index < 0 || index > size) {//index是可以等于size的相当于在末尾插入节点
            throw new IllegalArgumentException("index参数不正确");
        }

        /**
         * 首先将pre移动到要插入的节点的前一个节点上
         * 这里的移动我们按照移动的次数来看
         * i<index 说明最后一次进来的是index-1,开头是0,一共移动了index-1-0+1次,假设index=2
         * 代表移动了2次 从dummyhead移动一次就是[0]的位置,再移动一次就是[1]的位置
         * 正好是2前面的
         */
        Node pre = dummyHead;//找前一个位置要从dummyHead开始 pre直接指向dummyHead
        for (int i = 0; i < index; i++) {
            pre = pre.next;
        }
//            Node node = new Node(e);
//            node.next = pre.next;
//            pre.next = node;
        pre.next = new Node(e, pre.next);
        size++;
    }

    //在链表的末尾添加元素
    public void addLast(E e) {
        add(size, e);
    }

    //获得链表的第index(0-based)(从第0个开始)个位置的元素
    //不是常用操作
    public E get(int index){
        if (index < 0 || index >= size) {//index是不可以等于size的
            throw new IllegalArgumentException("index参数不正确");
        }
        Node cur = dummyHead.next;//找当前的位置要从dummyHead.next开始
        for(int i=0;i<index;i++){
            cur = cur.next;
        }
        //这里不会出现空指针异常的原因是如果链表为空,size=0;取头index=0 会抛出异常
        return cur.e;
    }

    public E getFirst(){
        return get(0);
    }

    public E getLast(){
        return get(size-1);
    }
    //修改链表的第index(0-based)(从第0个开始)个位置的元素
    //不是常用操作
    public void set(int index,E e){
        if (index < 0 || index >= size) {//index是不可以等于size的
            throw new IllegalArgumentException("index参数不正确");
        }
        Node cur = dummyHead.next;

        for(int i=0;i<index;i++){//移动index次
            cur = cur.next;
        }
        cur.e = e;
    }
    //查找链表中是否有元素e
    public boolean contains (E e){
        Node cur = dummyHead.next;
        while (cur!=null){
            if(cur.e.equals(e)) return true;
            cur = cur.next;//一定要移动啊.....
        }
        return false;
    }
    //修改链表的第index(0-based)(从第0个开始)个位置的元素
    //不是常用操作
    public E remove(int index){
        if(index<0||index>=size){
            throw new IllegalArgumentException("参数错误,删除失败");
        }
        //在删除的时候要维护pre这个指针
        Node pre = dummyHead;
        for(int i=0;i<index;i++){//循环执行完了pre就指向被删除节点的前一个
            pre = pre.next;
        }
        Node delNode = pre.next;
        pre.next = delNode.next;//直接跳过被删除的节点
        delNode.next = null;
        size--;
        return delNode.e;
    }
    public E removeFirst(){
        return remove(0);
    }
    public E removeLast(){
        return remove(size-1);
    }
    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("LinkedList : head [ ");
        Node cur = dummyHead.next;
        while(cur!=null){
            res.append(cur.e + " -> ");
            cur = cur.next;
        }
        res.append(" NULL ]");
        return res.toString();
    }
    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        linkedList.addFirst(1);
        linkedList.addFirst(2);
        linkedList.addFirst(3);
        linkedList.addFirst(4);
        System.out.println(linkedList);
        linkedList.add(2,100);
        System.out.println(linkedList);
        linkedList.remove(2);
        System.out.println(linkedList);
        linkedList.removeFirst();
        System.out.println(linkedList);
        linkedList.removeLast();
        System.out.println(linkedList);
    }
}
