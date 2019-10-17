package LinkedList;

public class LinkedListOld<E> {
    private class Node{
        public E e;
        public Node next;

        public Node(E e,Node next){
            this.e = e;
            this.next = next;
        }

        public Node(E e){
            this(e,null);
        }
        @Override
        public String toString(){
            return e.toString();
        }
    }
    //链表中的数据结构
    private Node head;//链表的头结点
    private int size;//链表中节点的个数

    public LinkedListOld(){
        head = null;
        size = 0;
    }

    public int getSize(){
        return size;
    }
    public boolean isEmpty(){
        return size == 0;
    }
    //在头部插入数据
    public void addFirst(E e){
//        Node node = new Node(e);
//        node.next = head;
//        head = node;
        head = new Node(e,head);//意思一样
        size++;
    }
    //在任意的"索引"index的位置上插入数据,关键在于找到index的前一个节点

    /**
     * 没有虚拟头结点的add,head是没有前一个节点的,要额外处理
     * @param index 索引
     * @param e value
     */
    public void add(int index,E e){
        if(index<0||index>size){//index是可以等于size的相当于在末尾插入节点
            throw new IllegalArgumentException("index参数不正确");
        }
        if(index == 0){
            addFirst(e);
        }else{
            Node pre = head;
            //首先将pre移动到要插入的节点的前一个节点上
            /**
             * 这里的移动我们按照移动的次数来看
             * i<index-1 说明最后一次进来的是index-2,开头是0,一共移动了index-2-0+1次,假设index=2
             * 代表移动了一次 从head[0] 移动一次就是[1]的位置,正好是index前面的
             */
            for(int i=0;i<index-1;i++){
                pre = pre.next;
            }
//            Node node = new Node(e);
//            pre.next = node.next;
//            pre.next = node;
            pre.next = new Node(e,pre.next);
            size++;
        }
    }
    //在链表的末尾添加元素
    public void addLast(E e){
        add(size,e);
    }

}
