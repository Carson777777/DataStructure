package Stack;

public interface Stack<E> {
    //入栈的方法
    void push(E e);
    //出栈的方法
    E pop();
    //检查栈顶元素的方法
    E peek();
    //返回容量的方法
    int getSize();
    //判断栈是否为空的方法
    boolean isEmpty();
}
