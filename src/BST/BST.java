package BST;


import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二分搜索树
 * 首先是二叉树,特点是左儿子比父亲小,右儿子比父亲大
 * 由于是搜索树,所以节点中装的数据必须是可比较的,也就是要实现comparable接口
 */
public class BST<E extends Comparable<E>>  {
    private class Node{
        E e;
        Node left,right;

        public Node(E e){
            this.e = e;
            left = null;
            right = null;
        }
    }

    private Node root;//根节点
    private int size;
    //构造函数
    public BST(){
        root = null;
        size = 0;
    }
    public int getSize(){
        return size;
    }
    public boolean isEmpty(){
        return size==0;
    }

    /**
     * 判断二叉搜索树中是否包含e
     * @param e 用户传入的e
     * @return true包含 false 不包含
     */
    public boolean contains(E e){
        return contains(root,e);
    }

    /**
     * 递归函数,宏观定义为 在以node为根的树里面寻找e 返回是否存在
     * @param node 根
     * @param e 传入的值
     * @return 布尔值
     */
    private boolean contains(Node node,E e){
        //递归终止条件 递归到底的时候也没有搜索到
        if(node == null){
            return false;
        }
        if(node.e.compareTo(e)==0){
            return true;
        }else if(node.e.compareTo(e)>0){
            return contains(node.left,e);
        }else {//(node.e.compareTo(e)<0)
            return contains(node.right, e);
        }
    }
    /**
     * 面向用户的添加一个节点的接口,
     * @param e 用户传入的新节点的数据
     */
    public void add(E e){
        root = add(root,e);
    }

    /**
     * 程序内部的递归调用添加节点的方法,不向用户开放
     * 递归函数的宏观定义是 向以root为根的二分搜索树中插入元素e,并且返回插入新节点后二分搜索树的根(当前层的)
     * @param root
     * @param e
     * @return
     */
    private Node add(Node root,E e){
        //递归终止条件为任何的位置上为null的时候要new一个新的节点挂接在二叉搜索树上
        if(root==null){
            root = new Node(e);
            size++;
            return root;
        }
        //递归逻辑
        if(e.compareTo(root.e)<0){//要插入的e比Node中的e小
            root.left = add(root.left,e);//向左子树插入
        }else if(e.compareTo(root.e)>0){//要插入的e比Node中的e大
            root.right = add(root.right,e);//向右子树插入
        }//两者相等的时候不用插入,什么也不做.
        return root;
    }

    /**
     * 前序遍历 前中后都是深度优先遍历,先到底
     */
    public void preOrder(){
        //如果要用list去记录,list声明在这里,并且把preOrder的参数加上一个list让他在每一层都有
        preOrder(root);
    }

    /**
     * 递归函数,宏观定义为以root为根进行前序遍历
     * @param root 每一层的根
     */
    private void preOrder(Node root) {
        //递归终止条件
        if (root == null) {
            return;
        }
        System.out.println(root.e);//对节点进行访问
        preOrder(root.left);//继续遍历当前节点的左子树
        preOrder(root.right);//继续遍历当前节点的右子树
    }

    /**
     * 前序遍历的迭代实现,需要用到辅助的数据结构栈
     * 利用栈存储下一次要访问的节点
     */
    public void preOrderNR(){
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            Node cur = stack.pop();
            System.out.println(cur.e);
            //因为栈先进后出,所以顺序是反的
            if(cur.right!=null){
                stack.push(cur.right);
            }
            if(cur.left!=null){
                stack.push(cur.left);
            }
        }
    }

    /**
     * 中序遍历 二分搜索树中序遍历的结果就是排序后的结果
     */
    public void inOrder(){
        inOrder(root);
    }
    /**
     * 递归函数,宏观定义为以root为根进行中序遍历
     * @param root 每一层的根
     */
    public void inOrder(Node root){
        if(root== null)
            return;
        inOrder(root.left);
        System.out.println(root.e);
        inOrder(root.right);
    }
    /**
     * 后序遍历 为二分搜索树释放内存
     */
    public void postOrder(){
        inOrder(root);
    }
    /**
     * 递归函数,宏观定义为以root为根进行后序遍历
     * @param root 每一层的根
     */
    public void postOrder(Node root){
        if(root== null)
            return;
        inOrder(root.left);
        inOrder(root.right);
        System.out.println(root.e);
    }

    /**
     * 层序遍历 需要借助辅助数据结构 队列
     * 单纯这样实现,层序遍历的层没有体现出来
     */
    public void levelOrder(){
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            Node cur = queue.remove();
            System.out.print(cur.e);
            if(cur.left!=null){
                queue.add(cur.left);
            }
            if(cur.right!=null){
                queue.add(cur.right);
            }
        }
    }

    /**
     * 真正分层的层序遍历,关键在于count值,记录的是当前层的节点个数
     */
      public void levelOrderTrue(){
          Queue<Node> queue = new LinkedList<>();
          queue.add(root);
          int count = 1;
          while(!queue.isEmpty()){
              for(int i = 0;i<count;i++){
                  Node cur = queue.remove();
                  System.out.print(cur.e);
                  if(cur.left!=null){
                      queue.add(cur.left);
                  }
                  if(cur.right!=null){
                      queue.add(cur.right);
                  }
              }
              count = queue.size();
              System.out.println();
          }
      }

    /**
     * 之字形打印二叉树 要用两个栈来分别记录当前层和下一层的
     *
     */
    public void levelOrderZ(){
        Stack<Node> stack1 = new Stack<>();
        Stack<Node> stack2 = new Stack<>();
        int count = 1;
        stack1.push(root);

        while(stack1.size()!=0 || stack2.size()!=0){
            if(stack1.size()!=0 && stack2.size()==0){
                for(int i=0;i<count;i++){
                    Node cur = stack1.pop();
                    System.out.print(cur.e);
                    if(cur.left!=null) stack2.push(cur.left);
                    if(cur.right!=null) stack2.push(cur.right);
                }
                count = stack2.size();
                System.out.println();
            }
            if(stack2.size()!=0 && stack1.size()==0){
                for(int i=0;i<count;i++){
                    Node cur = stack2.pop();
                    System.out.print(cur.e);
                    if(cur.right!=null) stack1.push(cur.right);
                    if(cur.left!=null) stack1.push(cur.left);
                }
                count = stack1.size();
                System.out.println();
            }
        }
    }

    /**
     * 查询二叉搜索树中最小的元素
     * @return
     */
    public E minimum(){
        if(root == null) throw new IllegalArgumentException("空树没有最小值");
        Node min = minimum(root);
        return min.e;
    }
    /**
     * 递归函数,宏观定义为,查询根为node的树中的最小值,并且返回这个最小值
     * @param  node 根
     * @return 最小值的节点
     */
    private Node
    minimum(Node node){
        //递归终止条件
        if(node.left==null) return node;
        return minimum(node.left);
    }

    /**
     * 查询二叉搜索树中最大的元素
     * @return
     */
    public E maximum(){
        if(root == null) throw new IllegalArgumentException("空树没有最大值");
        Node max = maximum(root);
        return max.e;
    }
    /**
     * 递归函数,宏观定义为,查询根为node的树中的最大值,并且返回这个最大值
     * @param  node 根
     * @return 最大值的节点
     */
    private Node maximum(Node node){
        //递归终止条件
        if(node.right==null) return node;
        return maximum(node.right);
    }

    /**
     * 删除二叉搜索树中最小的元素
     * @return 最小元素的值
     */
    public E removeMin(){
        E ret = minimum();//不用判空,因为minimum()中判空了已经
        root = removeMin(root);//必须用root来接,根据宏观定义,如果不接等于没删
        return ret;
    }

    /**
     * 递归函数,宏观定义为删除根为node的二叉搜索树中的最小值,返回删除节点之后的根
     * 递归终止条件里的node就是要被删除的那个节点 , 删除的方法就是用node的右子树替代node的值
     * 删除的时候分两种情况
     * 一 node为叶子节点,node.right==null 返回rightNode相当于返回null也就是删了
     * 二 node有右子树,本意就是用node的右子树去替代node的位置,逻辑更加没有问题
     * @param node 根
     * @return 删除节点之后的根
     */
    private Node removeMin(Node node){
        //递归终止条件
        if(node.left == null){
            //加上这两句是为了防止不能回收
            Node rightNode = node.right;
            node.right = null;
            //其实可以直接返回node.right
            size--;
            return rightNode;
        }
        //没有终止时,只是以左子树为根不断去递归删除
        node.left = removeMin(node.left);
        return node;
    }


    /**
     *  同理删除二叉搜索树中最大的值
     */
    public E removeMax(){
        E ret = maximum();//不用判空,因为minimum()中判空了已经
        root = removeMax(root);//必须用root来接,根据宏观定义,如果不接等于没删
        return ret;
    }

    private Node removeMax(Node node){
        //递归终止条件
        if(node.right == null){
            //加上这两句是为了防止不能回收
            Node leftNode = node.left;
            node.left = null;
            //其实可以直接返回node.right
            size--;
            return leftNode;
        }
        //没有终止时,只是以左子树为根不断去递归删除
        node.right = removeMax(node.right);
        return node;
    }

    /**
     * 删除用户传入的任意的e这个值
     * 删除任意的值和删除max和min最大的区别就是有可能被删除的节点既有左儿子又有右儿子此时要进行一个算法
     * 1 寻找到要删除的值e 把这个值设置为d
     * 2 要找到d的后继设置为s s是d的右子树里面最小的 s = findMin(d->right)
     * 3 删除s并把返回的节点作为s的右子树 s.right = removeMin(d->right)
     * 4 s.left -> d.left
     * 5 直接在递归函数中返回s就相当于删除了d
     *
     * 注意!!!!3和4一定不能颠倒,如果先执行4,删除的时候s就不会被删除了,链表成环会造成错误
     * @param e 用户传入要删除的值
     */
    public void remove(E e){
        root = remove(root,e);//一定要用根去接
    }

    /**
     * 递归函数 宏观定义为在以node为根的二叉搜索树中删除元素e,并且返回删除之后的根
     * @param node 根
     * @param e 待删除的节点
     * @return 有就返回node 没有就直接返回null
     */
    private Node remove(Node node,E e){
        //递归终止条件
        if (node == null){
            return null;
        }
        if(e.compareTo(node.e)<0){
            node.left = remove(node.left,e);
            return node;
        }else if (e.compareTo(node.e)>0){
            node.right = remove(node.right,e);
            return node;
        }else {
            if(node.right==null){
                size--;
                return node.left;
            }
            if(node.left==null) {
                size--;
                return node.right;
            }
            Node s = minimum(node.right);
            s.right = removeMin(node.right);
            s.left = node.left;

            node.left = node.right = null;

            size--;
            return s;
        }
    }
    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        generateBSTString(root,0,res);
        return res.toString();
    }
    //生成以node为根节点,深度为depth的描述二叉树的字符串
    private void generateBSTString(Node node,int depth,StringBuilder res){
        if(node == null){
            res.append(generateDepthString(depth)+"null\n");
            return;
        }
        res.append(generateDepthString(depth)+node.e+"\n");
        generateBSTString(node.left,depth+1,res);
        generateBSTString(node.right,depth+1,res);
    }
    private String generateDepthString(int depth){
        StringBuilder res = new StringBuilder();
        for(int i=0;i<depth;i++){
            res.append("--");
        }
        return res.toString();
    }

}
