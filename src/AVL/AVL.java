package AVL;
//满二叉树(),完全二叉树(二叉搜索树),平衡二叉树(线段树) 堆什么都不是

import java.util.ArrayList;

/**
 * AVL树整体上和BST树是一样的,变量上不一样当的地方在于每个节点要记录高度值和平衡因子
 * 高度值叶子节点的为1 父节点的高度值为左右子树高度较大的+1
 //平衡因子是左右子树高度值之差的绝对值,当平衡因子>1的时候,平衡二叉树的平衡就被打破了
 * 上面这句话是错误的理解,平衡因子是带正负号的,是左右子树的高度之差,确实当平衡因子绝对值>1的时候就不平衡了
 * 但是平衡因子绝对值>1只能用在判断isBalance这个函数中进行笼统的判断
 * 我们后面要进行左旋还是右旋是根据平衡因子的正负号进行的.
 * @param <K>
 * @param <V>
 */
public class AVL<K extends Comparable<K>,V> {
    private class Node{
         K key;
         V value;
         Node left,right;
        int height;


        public Node(K key,V value){
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            height = 1;//高度初始化为1,因为添加的位置一定是叶子节点的位置
        }
    }

    private Node root;
    private int size;

    public AVL(){
        root = null;
        size = 0;
    }

    public int getSize(){
        return size;
    }
    public boolean isEmpty(){
        return size==0;
    }
    public boolean contains(K key){
        Node node = getNode(root,key);
        return node!=null;
    }
    /**
     * 获得节点node的高度,如果节点传入null就返回0
     * @param node 传入节点
     * @return 节点高度
     */
    public int getheight(Node node){
        if(node==null) return 0 ;
        return node.height;
    }

    /**
     * 获得节点node的平衡因子 注意这里一定不要取绝对值
     * @param node
     * @return
     */
    public int getBalanceFactor(Node node){
        if(node==null) return 0 ;
        //return Math.abs(getheight(node.left)-getheight(node.right));
        return getheight(node.left)-getheight(node.right);
    }

    /**
     * 判断当前的树是不是一颗二分搜索树
     * 中序遍历的情况下应该是顺序的
     * @return true or false
     */
    public boolean isBST(){
        ArrayList<K> keys = new ArrayList<>();
        inOrder(root,keys);
        for(int i = 1;i<keys.size();i++){
            if (keys.get(i-1).compareTo(keys.get(i))>0) return false;
        }
        return true;
    }

    /**
     * 递归函数,宏观定义为,遍历以root为根的二叉树,并把访问的节点存入keys中
     * @param node
     * @param keys
     */
    private void inOrder(Node node,ArrayList<K> keys){
        if(node == null){
            return;
        }

        inOrder(node.left,keys);
        keys.add(node.key);
        inOrder(node.right,keys);
    }

    /**
     * 判断是否是平衡的,也就是平衡因子是否大于1
     * @return true or false
     */
    public boolean isBalanced(){
        return isBalanced(root);
    }

    /**
     * 递归函数,宏观定义为,判断以node为根的二叉树是不是平衡的
     * @param node 根
     * @return  返回true or false
     */
    private boolean isBalanced(Node node){
        if(node==null){//空树是平衡二叉树
            return true;
        }
        if(Math.abs(getBalanceFactor(node))>1){//这里根据是否大于1只能判断是否平衡的
            return false;
        }else{
            return isBalanced(node.left)&&isBalanced(node.right);
        }
    }

    /**
     * 进行右旋操作,y是不平衡的节点,
     * @param y 不平衡的节点
     * @return 右旋之后新树的节点
     */
    // 对节点y进行向右旋转操作，返回旋转后新的根节点x
    //        y                              x
    //       / \                           /   \
    //      x   T4     向右旋转 (y)        z     y
    //     / \       - - - - - - - ->    / \   / \
    //    z   T3                       T1  T2 T3 T4
    //   / \
    // T1   T2
    private Node rightRotate(Node y){
        if (y.left == null) System.out.println("出现了问题1");
        Node x = y.left;
        Node T3 = x.right;

        x.right = y;
        y.left = T3;
        //旋转之后要更新height,但是只需要更新y和x,并且要先y后x
        y.height = Math.max(getheight(y.left),getheight(y.right))+1;
        x.height = Math.max(getheight(x.left),getheight(x.right))+1;

        return x;
    }
    // 对节点y进行向左旋转操作，返回旋转后新的根节点x
    //    y                             x
    //  /  \                          /   \
    // T1   x      向左旋转 (y)       y     z
    //     / \   - - - - - - - ->   / \   / \
    //   T2  z                     T1 T2 T3 T4
    //      / \
    //     T3 T4
    private Node leftRotate(Node y){
        Node x = y.right;
        Node T2 = x.left;

        x.left = y;
        y.right = T2;

        y.height = Math.max(getheight(y.left),getheight(y.right))+1;
        x.height = Math.max(getheight(x.left),getheight(x.right))+1;

        return x;
    }

    //增删改查
    /**
     * 用户接口,add只添加Key和Value
     * @param key 键
     * @param value 值
     */
    public void add(K key,V value){
        root = add(root,key,value);//要用root去接,否则连不起来
    }

    /**
     * 递归函数,宏观定义为在以node为根二叉搜索树上添加key和value,返回添加之后的根root
     * @param node 递归中每一层的root
     * @param key 要添加的键
     * @param value 要添加的值
     * @return 返回添加之后的根root
     */
    private Node add(Node node,K key,V value){
        //递归终止条件
        if (node==null){
            node = new Node(key,value);
            size++;
            return node;
        }
        //递归逻辑条件
        if(key.compareTo(node.key)<0){
            node.left = add(node.left,key,value);
        }else if(key.compareTo(node.key)>0){
            node.right = add(node.right,key,value);
        }else {
            node.value = value;
        }//如果相等,只更新value,不插入重复的节点


        //对当前node为根的height值进行更新
        node.height = Math.max(getheight(node.left),getheight(node.right))+1;
        //计算平衡因子
        int balanceFactor = getBalanceFactor(node);
        //平衡维护
        //LL
        if (balanceFactor > 1 && getBalanceFactor(node.left)>=0){//不平衡节点的左侧的左侧搞事,右旋
            return rightRotate(node);
        }
        //RR
        if (balanceFactor<-1 && getBalanceFactor(node.right)<=0){//不平衡节点的右侧的右侧搞事,左旋
            return leftRotate(node);
        }
        //不平衡节点左侧的右侧搞事没判断,同理不平衡节点右侧的左侧搞事也没判断.....

        //LR 先对node的left进行左旋变化成LL
        if (balanceFactor>1 && getBalanceFactor(node.left)<0){
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        //RL 先对node的right进行右旋变化成RR
        if (balanceFactor<-1 && getBalanceFactor(node.right)>0){
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    /**
     * 用户接口 通过键获取值
     * @param key key为用户传入的键
     * @return 返回value
     */
    public V get(K key){
        Node node = getNode(root,key);
        return node==null ? null : node.value;
    }

    /**
     * 递归函数,宏观定义为在以node为根的二叉搜索树中寻找键为key的节点,并返回查找到的节点
     * @param node 每层的根root
     * @param key 键
     * @return 查找到的节点 如果找不到就返回null
     */
    private Node getNode(Node node,K key){
        //递归终止条件
        if(node==null){
            return null;
        }
        //递归逻辑
        if(key.compareTo(node.key)==0){
            return node;
        }else if(key.compareTo(node.key)<0){
            return getNode(node.left,key);
        }else {
            return getNode(node.right,key);
        }
    }

    /**
     * 用户接口,用户将键为key的值改变为value
     * @param key 键
     * @param value 值
     */
    public void set(K key,V value){
        Node node = getNode(root,key);
        if (node!=null){
            node.value = value;
        }else {
            throw new IllegalArgumentException("要修改的节点不存在");
        }
    }

    /**
     * 用户接口,用户传入key,删除以key为键的节点.(删除任意节点)
     * @param key 键
     */
    public void remove(K key){
        root = remove(root,key);
    }

    /**
     * 递归函数,宏观定义为删除以node为根的二叉搜索树中以key为键的节点,返回删除后的根
     * @param node 根
     * @param key 键
     * @return 删除后的根
     */
    private Node remove(Node node,K key){
        if(node == null){
            return null;
        }
        Node retNode;//所有要返回的节点都先放到这里面判断
        if(key.compareTo(node.key)==0){
            if(node.left == null){
                size--;
                retNode = node.right;
            }else if (node.right == null){
                size--;
                retNode =  node.left;
            }else {
                //要删除的节点左右子树都存在
                //1,找到后继节点
                //2,在原右子树中删除后继节点 连在后继节点
                //3,原左子树连接在后继节点
                //4,返回后继节点
                Node s = minimum(node.right);
                //s.right = removeMin(node.right); removeMin中是没有维护平衡方法的,解决方案是接着调用remove
                s.right = remove(node.right,s.key);//在node的右子树中删除s.key这个键
                s.left = node.left;
                retNode = s;
            }
        }else if(key.compareTo(node.key)<0){
            node.left = remove(node.left,key);
            retNode =  node;
        }else {
            node.right = remove(node.right,key);
            retNode =  node;
        }
        //删除后返回的节点可能是空的,如果是空的就不用再判断平衡性了
        if(retNode == null){
            return null;
        }
        //更新当前node的height
        retNode.height = Math.max(getheight(retNode.left),getheight(retNode.right))+1;
        //计算平衡因子
        int balanceFactor = getBalanceFactor(retNode);
        //平衡维护
        //LL
        if (balanceFactor > 1 && getBalanceFactor(retNode.left)>=0){
            return rightRotate(retNode);
        }
        //RR
        if (balanceFactor < -1 && getBalanceFactor(retNode.right)<=0){
            return leftRotate(retNode);
        }
        //LR
        if (balanceFactor > 1 && getBalanceFactor(retNode.left)<0){
            retNode.left = leftRotate(retNode.left);
            return rightRotate(retNode);
        }
        //RL
        if (balanceFactor < -1 && getBalanceFactor(retNode.right)>0){
            retNode.right = rightRotate(retNode.right);
            return leftRotate(retNode);
        }
        return retNode;
    }
    private Node minimum(Node node){
        if (node.left ==null){
            return node;
        }
        return minimum(node.left);
    }
    public void removeMin(){
        root = removeMin(root);
    }

    private Node removeMin(Node node){
        if (node.left == null){
            return node.right;
        }

        node.left = removeMin(node.left);
        return node;
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
        res.append(generateDepthString(depth)+node.key+"\n");
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

    public static void main(String[] args){

        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("E:\\AllProjects\\玩转数据结构\\src\\pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

            AVL<String,Integer> map = new AVL<String,Integer>();
            for (String word : words) {
                if (map.contains(word))
                    map.set(word, map.get(word) + 1);
                else
                    map.add(word, 1);
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
            System.out.println(map.isBalanced());
            for(String word:words){
                map.remove(word);
                if(!map.isBalanced() || !map.isBST()){
                    throw new RuntimeException("Error");
                }
            }
        }

        System.out.println();
    }
}

