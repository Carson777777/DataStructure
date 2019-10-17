package HeapAndPriorityQueue;

/**
 * 基于数组实现的大顶堆
 * @param <E>
 */
public class MaxHeap <E extends Comparable<E>>{
    private Array<E> data;

    public MaxHeap(int capacity){
        data = new Array<>(capacity);
    }

    public MaxHeap(){
        data = new Array<>();
    }

    /**
     * 这个构造函数里使用的是Heapify操作
     * 也就是说把传入的数组组装成一个大顶堆 这个操作整体的复杂度是O(n)的
     *如果简单直观理解的话，我们可以看出来，heapify的过程首先忽略了所有的叶子节点，
     * 所以近一半的节点没有考虑。对于考虑的所有结节（非叶子结点），也并不是每一个节点都需要logN次操作。
     * 最下面一排非叶子结点只需要1次操作；倒数第二层非叶子结点只需要2次操作，以此类推...，只有最后的根节点，
     * 需要logN次操作。所以整体远远小于N个节点需要操作，大多数需要操作的节点，操作数也远远小于logN次。
     * 注意：这个过程不能证明heapify的时间复杂度要比O(NlogN)低，只是一个感性的理解：）
     * heapify的时间复杂度其实是O(N)的，是小于O(NlogN)的 也就是小于我们把一个一个元素直接add到堆里的复杂度
     * @param arr 传入的数组
     */
    public MaxHeap(E[] arr){
        data = new Array<>(arr);
        for(int i = parent(data.getSize()-1);i>=0;i--){
            siftDown(i);
        }
    }

    //返回堆中元素的个数
    public int getsize(){
        return data.getSize();
    }

    //判断堆是否为空
    public boolean isEmpty(){
        return data.isEmpty();
    }

    //在以0为下标开始的二叉堆中求一个节点的父节点的下标
    public int parent(int index){
        if(index == 0){
            throw new IllegalArgumentException("根节点没有父节点");
        }
//        if(index<0||index>data.getSize()-1){
//            throw new IllegalArgumentException("输入的下标值不存在于堆中");
//        }
        return (index-1)/2;
    }

    //在以0为下标开始的二叉堆中求一个节点的左儿子的下标
    public int leftChild(int index){
        return index*2+1;
    }

    //在以0为下标开始的二叉堆中求一个节点的右儿子的下标
    public int rightChild(int index){
        return index*2+2;
    }

    //向堆中添加元素
    public void add(E e){
        data.addLast(e);//在数组的末尾直接添加一个元素
        //siftUp(data.getSize()-1);//进行上浮的操作
        siftUpR(data.getSize()-1);
    }

    /**
     * 注意上浮和下沉操作是private的,上浮和下沉的操作根本不用判断传入的index是否是合理的,因为一定是合理的
     */
    /**
     * 对一个下标为index的元素进行上浮操作时间复杂度是O(logn)
     * 上浮操作就是将index的值和父节点的值进行比较,如果大于父节点的值,就和父节点交换位置,意为上浮
     * @param index 要上浮的下标
     */
    private void siftUp(int index){
        while(index > 0 && data.get(index).compareTo(data.get(parent(index)))>=0){
            data.swap(index,parent(index));
            index = parent(index);
        }
    }
    //上浮操作的递归写法
    private void siftUpR(int index){
        if(index == 0 || data.get(index).compareTo(data.get(parent(index)))<0){
            return;
        }
        data.swap(index,parent(index));
        siftUpR(parent(index));
    }

    //取得最大的元素
    public E findMax(){
        if(data.getSize() == 0)
            throw new IllegalArgumentException("Can not findMax when heap is empty.");
        return data.get(0);
    }

    //删除堆中最大的元素并返回
    public E  extractMax(){
        E ret = findMax();
        data.swap(0,data.getSize()-1);
        data.removeLast();//O(1)的操作
        siftDown(0);
        return ret;
    }

    /**
     * 下沉操作,父节点与左右儿子中较大的那个相比,如果父节点的值大,则下沉立即停止
     * 如果父节点的值小,交换位置,然后继续进行下沉,下沉的终止条件是下沉的节点并非叶子节点
     * 下沉操作的时间复杂度是O(logn),因为只需要进行深度次操作
     * @param k 要下沉操作的节点下标值
     */
    private void siftDown(int k){
        //循环的条件是一定有儿子,没儿子叶子节点不能下沉,有儿子才能下沉,有儿子就一定有左儿子,但不一定有右儿子
        //因为是完全二叉树
        while(leftChild(k)<data.getSize()){//一定有左儿子,不一定有右儿子
            int j = leftChild(k);
            if(rightChild(k)<data.getSize() && data.get(rightChild(k)).compareTo(data.get(leftChild(k)))>0){
                j = rightChild(k);
            }
            //此时j是代表左右儿子值最大的那个的下标
            if(data.get(j).compareTo(data.get(k))<=0){
                break;
            }else{
                data.swap(j,k);
                k = j;
            }
        }
    }
    /**
     * 将最大的元素取出,并且返回,然后添加一个新的元素
     * 有两种思路 1 先extractMax,再add 相当于进行了一次下沉,又进行了一次上浮 时间复杂度为 两次O(logn)
     *            2 将要添加元素和堆顶的元素进行交换,然后直接进行下沉,时间复杂度为一次O(logn)
     *            这里使用第二种思路
     * @param e 要添加的新元素
     * @return  取出的最大元素
     */
    public E replace(E e){
        E ret = findMax();
        data.set(0,e);
        siftDown(0);
        return ret;
    }
}
