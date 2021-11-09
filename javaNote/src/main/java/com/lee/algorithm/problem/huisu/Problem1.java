package com.lee.algorithm.problem.huisu;



import java.util.ArrayList;
import java.util.List;


/**
 * 回溯法
 * 给定一个数字数组，返回手机9键键盘对应的所能拼接成的字符串
 * 例如 输入[2,3] 返回[ad,ae,af,bd,be,bf,cd,ce,cf]
 */
public class Problem1 {

    private static final String[] source = {" ", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    private static List<String> result = new ArrayList<>();

    private static List<String> solve(String str, int index, String s) {

        if (index == str.length()) {
            result.add(s);
            return result;
        }

        System.out.println(str + ":" + index + "===");
        char c = str.charAt(index);
        System.out.println(str + ":" + index + ":" + c);
        String string = source[c - '0'];
        for (int i = 0; i < string.length(); i++) {
            solve(str, index + 1, s + string.charAt(i));
        }
        return result;
    }

    public static void main(String[] args) {
        List<String> solve = solve("567", 0, "");
        System.out.println("solve = " + solve);
    }
}
