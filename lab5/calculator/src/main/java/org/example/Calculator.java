package org.example;

import java.util.Deque;
import java.util.LinkedList;

public class Calculator {
    private final Deque<Double> stack = new LinkedList<>();

    public void push(Object arg) {
        if (arg instanceof String) {
            String operator = (String) arg;
            double y = stack.removeLast();
            double x = stack.isEmpty() ? 0 : stack.removeLast();
            double result;
            switch (operator) {
                case "-":
                    result = x - y;
                    break;
                case "+":
                    result = x + y;
                    break;
                case "*":
                    result = x * y;
                    break;
                case "/":
                    result = x / y;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operator: " + operator);
            }
            stack.add(result);
        } else if (arg instanceof Number) {
            stack.add(((Number) arg).doubleValue());
        } else {
            throw new IllegalArgumentException("Invalid argument type: " + arg.getClass());
        }
    }

    public Number value() {
        return stack.getLast();
    }
}
