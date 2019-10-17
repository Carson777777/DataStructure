package BST;

public class Main {
    public static void main(String[] args) {
        BST<Integer> bst = new BST<Integer>();
        int[] array = {5,3,6,2,4,8};
        for(int num:array){
            bst.add(num);
        }
        /**
         *        5
         *     /    \
         *    3      6
         *   /\       \
         * 2 4         8
         */
//        bst.preOrder();
//        System.out.println();
//        bst.preOrderNR();
//        bst.levelOrder();
//        bst.levelOrderTrue();
//        System.out.println();
//        bst.levelOrderZ();
//          int i= bst.minimum();
//        System.out.println(i);
//        int j = bst.maximum();
//        System.out.println(j);
//        bst.removeMin();
//        bst.levelOrderTrue();
//        bst.removeMin();
//        bst.levelOrderTrue();
//        bst.removeMax();
//        bst.levelOrderTrue();
//        System.out.println(bst.contains(2));
//        System.out.println(bst.contains(1));
        bst.remove(5);
        bst.levelOrderTrue();
       // System.out.println(bst);
    }

}
