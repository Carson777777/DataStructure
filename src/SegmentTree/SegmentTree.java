package SegmentTree;

public class SegmentTree<E> {

    private E[] data;
    private E[] tree;
    private Merge<E> merge;

    public SegmentTree(E[] arr,Merge<E> merge){
        this.merge = merge;
        data = (E[]) new Object[arr.length];
        for(int i=0;i<data.length;i++){
            data[i] = arr[i];
        }
        tree = (E[])new Object[4*arr.length];
        buildSegmentTree(0,0,data.length-1);
    }

    public int getSize(){
        return data.length;
    }

    public E get (int index){
        if(index<0||index>=data.length){
            throw new IllegalArgumentException("参数不合法");
        }
        return data[index];
    }
    //返回完全二叉树的数组表示中,一个索引所表示的元素的左孩子节点的索引
    private int leftChild(int index){
        return index*2+1;
    }

    private int rightChild(int index){
        return index*2+2;
    }

    /**
     * 递归函数,宏观定义为,在treeIndex的位置上创建表示区间[l,r]的线段树
     */

    private void buildSegmentTree(int treeIndex,int l,int r){
        //递归终止条件
        if(l==r){
            tree[treeIndex] = data[l];
            return;
        }

        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);
        int mid = l+(r-l)/2;
        buildSegmentTree(leftTreeIndex,l,mid);
        buildSegmentTree(rightTreeIndex,mid+1,r);

        tree[treeIndex] = merge.merge(tree[leftTreeIndex],tree[rightTreeIndex]);
    }

    /**
     * 用户接口,用户只需要传入要查询的区间[queryL,queryR]
     * @param queryL 查询的左侧
     * @param queryR 查询的右侧
     * @return 查询的结果
     */
    public E query(int queryL,int queryR){
        return query(0,0,data.length-1,queryL,queryR);
    }

    /**
     * 递归函数,宏观定义为:查询root为treeIndex 范围为[l,r]中的[queryL,queryR]
     * 我们要注意,这个范围始终是data的范围,而不是tree的范围
     * @param treeIndex treeIndex为查询开始的位置
     * @param l 递归中变化的范围
     * @param r 递归中变化的范围
     * @param queryL 用户输入的范围(不变)
     * @param queryR 用户输入的范围(不变)
     * @return 返回值
     */
    private E query(int treeIndex,int l,int r,int queryL,int queryR){
        //递归的终止条件
        if(l==queryL&&r==queryR){
            return tree[treeIndex];
        }
        //递归每一层的逻辑
        // treeIndex的节点分为[l...mid]和[mid+1...r]两部分
        int mid = l+(r-l)/2;
        int leftIndex = leftChild(treeIndex);
        int rightIndex = rightChild(treeIndex);
        if (queryL>=mid+1){
            return query(rightIndex,mid+1,r,queryL,queryR);
        }else if(queryR<=mid){
            return query(leftIndex,l,mid,queryL,queryR);
        }else{//如果要查询的区间横跨了左右子树的两部分区间
            E leftResult = query(leftIndex,l,mid,queryL,mid);
            E rightResult = query(rightIndex,mid+1,r,mid+1,queryR);
            return merge.merge(leftResult,rightResult);
        }
    }

    /**
     * 用户接口 将index位置的值,更新为e
     * @param index 这个index是data中的index和tree中的index没有对应关系
     * @param e 要更新的值
     */
    public void set(int index,E e){
        if(index<0||index>=data.length) throw new IllegalArgumentException("参数不合法");
        data[index] = e;
        //接下来要更新tree中的值
        set(0,0,data.length,index,e);
    }

    /**
     * 递归函数,宏观定义是 以treeIndex为根,[l,r]的范围内,更新index的值为e
     * @param treeIndex 根
     * @param index 更新的index
     * @param l data中的l
     * @param r data中的r
     * @param e 更新的值
     */
    private void set(int treeIndex,int l,int r, int index,E e){
        //递归终止条件
        if(l==r){//找到了要更新的节点
            tree[treeIndex] = e;
            return;
        }
        //递归逻辑
        int mid = l+(r-l)/2;
        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);
        if (index<=mid){
            set(leftTreeIndex,l,mid,index,e);
        }else{// if(index>=mid+1)
            set(rightTreeIndex,mid+1,r,index,e);
        }
        tree[treeIndex] = merge.merge(tree[leftTreeIndex],tree[rightTreeIndex]);
    }
    @Override
    public String toString(){
        StringBuilder ret = new StringBuilder();
        ret.append("segmentTree: [ ");
        for(int i = 0;i<tree.length;i++){
            ret.append(tree[i]);
            if(i!=tree.length-1){
                ret.append(", ");
            }
        }
        ret.append(" ]");
        return ret.toString();
    }

}
