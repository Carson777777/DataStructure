package Array;

public class Main {
    /**
     * 直接使用java中的数组
     * @param args
     */
    public static void main(String[] args) {
//        int [] arr = new int [10];
//        for (int i = 0; i <arr.length ; i++) {
//            arr[i] = i;
//        }
//        int [] scores = new int[] {100,99,66};
//        int [] scores1 = {100,99,66};
//        for (int score: scores) {
//            System.out.println(score);
//        }
//        scores1[1] = 100;
//
        //测试自己封装的类
        Array<Integer> array = new Array();
        for(int i=0;i<10;i++){
            array.addLast(i+1);
        }
        System.out.println(array);
        array.addLast(11);
        System.out.println(array);
        array.removeLast();
        //array.removeLast();
        System.out.println(array);
//        System.out.println(array);
//        array.add(1,10);
//        System.out.println(array);
//
//        System.out.println(array.get(1));
//        array.set(1,100);
//        System.out.println(array);
//        System.out.println(array.find(1));//找到1的下标是0
//        System.out.println(array.containsbi(2));
//        System.out.println(array.removeFirst());
//        System.out.println(array.removeLast());
//        array.removeElement(4);
//        System.out.println(array);
    }
}
