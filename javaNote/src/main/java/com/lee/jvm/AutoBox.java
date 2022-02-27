package com.lee.jvm;

import com.lee.eo.User;
import org.junit.Test;

public class AutoBox {

    Object monitor = new Object();

    @Test
    public void test() throws InterruptedException {

        User user = new User();

        Thread t = new Thread(() -> {
            while (true) {
                System.out.println("11111");
                try {
                    synchronized (user) {
                        user.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();

    }

}
