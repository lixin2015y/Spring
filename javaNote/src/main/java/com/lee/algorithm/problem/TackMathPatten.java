package com.lee.algorithm.problem;

import javax.swing.text.MaskFormatter;
import javax.xml.transform.sax.SAXTransformerFactory;
import java.util.HashMap;
import java.util.Stack;

/**
 * 使用栈来撇配括号
 */
public class TackMathPatten {

    public static Boolean match(String[] arr) {

        HashMap<String, String> mapRight = new HashMap<>(16);
        mapRight.put("]", "[");
        mapRight.put("}", "{");
        mapRight.put(")", "(");
        HashMap<String, String> mapLeft = new HashMap<>(16);
        mapLeft.put("[", "]");
        mapLeft.put("{", "}");
        mapLeft.put("(", ")");

        if (arr.length == 0) {
            return true;
        }
        Stack<String> stack = new Stack<String>();
        for (int i = 0; i < arr.length; i++) {
            if (mapLeft.containsKey(arr[i])) {
                stack.push(arr[i]);
            } else {
                if (stack.peek() == null) {
                    return false;
                }
                String pop = stack.pop();
                if (!mapRight.get(arr[i]).equals(pop)) {
                    return false;
                }
            }
        }

        if (stack.size() != 0) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String[] arr = {"[","[", "[", "(", ")", "]", "]"};
        Boolean match = match(arr);
        System.out.println(match);
    }
}
