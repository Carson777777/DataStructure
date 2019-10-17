package Stack;

import java.util.Queue;

public class Main {
    public static void main(String[] args) {
//        Stack<Integer> stack = new ArrayStack<Integer>();
//        for (int i = 0; i <5 ; i++) {
//            stack.push(i);
//        }
//
//        System.out.println(stack);
//        stack.pop();
//        System.out.println(stack);

        Stack<Integer> stack = new LinkedListStack<Integer>();
        for (int i = 0; i <5 ; i++) {
            stack.push(i);
        }

        System.out.println(stack);
        stack.pop();
        System.out.println(stack);
    }
}
