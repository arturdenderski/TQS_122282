package org.example;

import java.util.LinkedList;

public class TqsStack {

    private LinkedList<Integer> list;

    public TqsStack() {
        list = new LinkedList<Integer>();
    }

    public void push(int x) {
        list.add(x);
    }

    public int pop() {
        return list.pop();
    }

    public int size() {
        return list.size();
    }


    public boolean isEmpty() {
        return list.size() == 0;
    }

    public int peek() {
        return list.getLast();
    }

}