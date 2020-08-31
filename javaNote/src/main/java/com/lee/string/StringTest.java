package com.lee.string;

import com.lee.eo.User;
import org.junit.Test;

import javax.sound.midi.Soundbank;

public class StringTest {

    @Test
    public void test() {
        String a = "123";
        String b = "123";
        String c = new String("123");
        String d = new String("123");
        System.out.println(a == b);
        System.out.println(a == c);
        System.out.println(c == d);
    }

    static int a = 0;
    int b = 1;

    static void testStatic() {
        System.out.println(a);
        System.out.println(new StringTest().b);
    }

    void testPublic() {
        System.out.println(a);
        System.out.println(b);
    }

    @Test
    public void test3() {
//        final String a = "";
//        a = "v";

        final User user = new User();
        user.setEmail("12312321");

    }


}
