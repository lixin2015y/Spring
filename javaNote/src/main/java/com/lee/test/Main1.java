package com.lee.test;

import org.junit.Test;

import java.util.Scanner;

/**
 * @className: Main1
 * @author: li xin
 * @date: 2021-12-28
 **/
public class Main1 {
    public static void main(String[] args) {
        int[] freq = new int[26];
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()) {
            String str = sc.nextLine();
            for (int i = 0; i < str.length(); i++) {
                freq[str.charAt(i) - 'a']++;
            }
            int minFreq = Integer.MAX_VALUE;
            for (int i =0; i < freq.length; i++) {
                if (freq[i] != 0 && freq[i] < minFreq) {
                    minFreq = freq[i];
                }
            }

            for (int i = 0; i < str.length(); i++ ) {
                int n = str.charAt(i) - 'a';
                if (freq[n] != minFreq) {
                    System.out.print(str.charAt(i));
                }
            }
            System.out.println("");
        }

    }

    @Test
    public void test(){
        System.out.println('s' - 'a');
    }

    @Test
    public void test3(){
        int[][] ints = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        for (int[] anInt : ints) {
            for (int i : anInt) {
                System.out.print(i + " ");
            }
            System.out.print("\n");
        }
        System.out.println(ints[0][1]);
        System.out.println(ints[1][0]);
    }
}
