package Stack;

import Array.Array;

public class ArrayStack<E> implements Stack<E> {

    private Array<E> array;

    public ArrayStack(int capacity){
        array = new Array<E>(capacity);
    }

    public ArrayStack(){
        array = new Array<E>();
    }


    @Override
    public void push(E e) {
        array.addLast(e);
    }

    @Override
    public E pop() {
        if (!array.isEmpty()){
            return array.removeLast();
        }else throw new IllegalArgumentException("栈为空不能取数据");
    }

    @Override
    public E peek() {
        if(!array.isEmpty()){
            return array.getLast();
        }else throw new IllegalArgumentException("栈为空不能取数据");
    }

    @Override
    public int getSize() {
        return array.getSize();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    public int getCapacity(){
        return array.getCapacity();
    }
    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("stack: ");
        res.append("[ ");
        for(int i=0;i<array.getSize();i++){
            res.append(array.get(i));
            if(i!= array.getSize()-1){
                res.append(", ");
            }
        }
        res.append("] top");
        return res.toString();
    }
}
