package com.lee.controller;

import org.junit.Test;
import org.springframework.stereotype.Controller;

@Controller
public class BookController {

    public static void main(String[] args) {
        Integer a = 123456;
        String b = String.valueOf(a);
        StringBuilder stringBuilder = new StringBuilder(b);
        StringBuilder result = stringBuilder.reverse();
        System.out.println(result);
    }

    @Test
    public void test(){
        int a = 1;
        Integer b = 1;
        System.out.println(a == b);
    }


}
