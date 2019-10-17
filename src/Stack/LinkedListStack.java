package Stack;

import LinkedList.LinkedList;

public class LinkedListStack<E> implements Stack<E>{

    private LinkedList<E> linkedList;

    public LinkedListStack(){
        linkedList = new LinkedList<E>();
    }
    @Override
    public void push(E e) {
        linkedList.addFirst(e);
    }

    @Override
    public E pop() {
        return linkedList.removeFirst();
    }

    @Override
    public E peek() {
        return linkedList.getFirst();
    }

    @Override
    public int getSize() {
        return linkedList.getSize();
    }

    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("stack : ");
        res.append(linkedList.toString());
        return res.toString();
    }
}
