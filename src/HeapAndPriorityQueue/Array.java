package HeapAndPriorityQueue;

public class Array<E>{
    private E [] data;//java的自带数组
    private int size;//有效的元素的个数

    /**
     * 构造函数,传入数组的容量capacity 构造Array
     * @param capacity
     */
    public Array(int capacity){
        data = (E[]) new Object[capacity];//不支持直接new一个泛型数组
        size = 0;
    }

    /**
     * 无参构造函数 默认数组的容量是10
     */
    public Array(){
        this(10);
    }

    /**
     * 构造函数,用数组构造数组
     * @param arr 传入的数组
     */
    public Array(E[] arr){
        data = (E[])new Object[arr.length];
        for(int i = 0 ; i < arr.length ; i ++)
            data[i] = arr[i];
        size = arr.length;
    }
    //获取数组中的有效元素的个数
    public int getSize(){
        return size;
    }
    //获取数组的容量
    public int getCapacity(){
        return data.length;
    }
    //返回数组是否为空
    public boolean isEmpty(){
        return size==0;
    }
    //在所有的元素之后插入一个元素
    public void addLast(E e){
       add(size,e);
    }
    public void addFirst(E e){
        add(0,e);
    }
    //在任意的index插入一个元素
    public void add(int index,E e){

        if(index < 0 || index > size){//数组中要保证存的数据是连续的所以应该是比size要小的
            throw new IllegalArgumentException("add fail,index must >=0 or <= size");
        }
        //扩容的操作放在参数判断的后面,因为先判断参数,防止不正确的参数(一个非常大的size)导致先扩容了,浪费空间
        if(size == data.length){
            resize(data.length*2);//扩容两倍
        }
        //判断这几个参数
        //首先i=size-1时 表达式为data[size] = data[size-1],最后的有数的赋值给了第一个没有数的 这是交换的开始
        //之后判断i是不是要等于index的时候假设等于,最后一次进循环就是i=index 表达式为data[index+1] = data[index]
        //这一句吧index中的值复制了,应该是最后一步,所以是需要等于的
        for(int i = size-1;i>=index;i--){
            data[i+1] = data[i];
        }
        data[index] = e;
        size++;
    }
    //取得index位置的元素
    public E get(int index){
        if (index<0||index>=size){
            throw new IllegalArgumentException("参数错误");
        }
        return data[index];
    }
    public E getFirst(){
        return get(0);
    }
    public E getLast(){
        return get(size-1);
    }
    //修改index位置的元素为e
    public void set(int index,E e){
        if (index<0||index>=size){
            throw new IllegalArgumentException("参数错误");
        }
        data[index] = e;
    }
    //判断e在数组里第一次出现的位置 (使用O(n)级别的暴力遍历)
    //如果没有就返回-1
    public int find(E e){
        for (int i=0;i<size;i++){
            if(data[i].equals(e)){//使用泛型之后,==要改成equals,引用判定和值判定,对于对象值判定比较合理
                return i;
            }
        }
        return -1;
    }
    //判断e是否包含在数组里(使用O(logn)级别的二分查找法) 用二分查找的话,
    public boolean containsbi(E e){
        int index = find(e);
        if(index!=-1){
            return true;
        }
        return false;
    }
    //删除index下标的元素,并返回删除元素
    public E remove(int index){
        if(index < 0 || index >= size){
            throw new IllegalArgumentException("index参数输入错误");
        }
        E ret = data[index];
        for(int i = index+1;i<size;i++){
            data[i-1] = data[i];
        }
        size--;
        data[size] = null;//实际这里还是有一个引用的,这个其实是游离数据,而不是内存泄漏
        //动态的缩容 之所以动态缩容要变成1/4是为了防止复杂度的震荡
        if(size == data.length/4 && data.length/2!=0){//java不能支持new一个capacity为0的数组
            resize(data.length/2);
        }
        return ret;
    }
    //从数组中删除头元素
    public E removeFirst(){
        return remove(0);
    }
    //从数组的末尾删除元素
    public E removeLast(){
        return remove(size-1);
    }
    //删除某个用户已知的元素
    public void removeElement(E e){
        int index = find(e);
        if (index!=-1){
            remove(index);
        }
    }
    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append(String.format("Array : 数组中有效位数:%d 数组的容量 %d \n",size,data.length));
        res.append("[ ");
        for(int i = 0;i<size;i++){
            res.append(data[i]);
            if(i!=size-1){
                res.append(", ");
            }
        }
        res.append(" ]");
        return res.toString();
    }

    public void resize(int resize){
        E[] newdata = (E[]) new Object[resize];
        for(int i=0;i<size;i++){
            newdata[i] = data[i];
        }
        data = newdata;
    }

    public void swap(int i, int j){

        if(i < 0 || i >= size || j < 0 || j >= size)
            throw new IllegalArgumentException("Index is illegal.");

        E t = data[i];
        data[i] = data[j];
        data[j] = t;
    }
}
