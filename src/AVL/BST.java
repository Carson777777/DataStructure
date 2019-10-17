package AVL;

public class BST<K extends Comparable<K>,V> {
    private class Node{
        K key;
        V value;
        Node left,right;

        public Node(K key,V value){
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

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

        if(key.compareTo(node.key)==0){
            if(node.left == null){
                size--;
                return node.right;
            }else if (node.right == null){
                size--;
                return node.left;
            }
            //要删除的节点左右子树都存在
            //1,找到后继节点
            //2,在原右子树中删除后继节点 连在后继节点
            //3,原左子树连接在后继节点
            //4,返回后继节点
            Node s = minimum(node.right);
            s.right = removeMin(node.right);
            s.left = node.left;
            return s;
        }else if(key.compareTo(node.key)<0){
            node.left = remove(node.left,key);
            return node;
        }else {
            node.right = remove(node.right,key);
            return node;
        }
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

    public static void main(String[] args) {
        BST<Integer,Integer> bst = new BST<Integer,Integer>();
        int[] array = {5,3,6,2,4,8};
        for(int num:array){
            bst.add(num,null);
        }
        System.out.println(bst);
        bst.removeMin();
        System.out.println(bst);
    }
}
