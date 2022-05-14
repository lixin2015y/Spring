package com.lee.test;

import org.junit.Test;

import java.sql.SQLOutput;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static void main(String[] args) {

        String patten = "[WASD]\\d{1}\\d?";
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()) {
            int x = 0;
            int y = 0;
            String str = sc.nextLine();
            String[] commonds = str.split(";");
            for (int i = 0; i < commonds.length; i++) {
                if (commonds[i].matches(patten)) {
                    if(commonds[i].startsWith("W")) {
                        y += Integer.parseInt(commonds[i].substring(1));
                    } else if(commonds[i].startsWith("S")) {
                        y -= Integer.parseInt(commonds[i].substring(1));
                    } else if(commonds[i].startsWith("A")) {
                        x -= Integer.parseInt(commonds[i].substring(1));
                    } else {
                        x += Integer.parseInt(commonds[i].substring(1));
                    }
                }
            }
            System.out.println(x + "," + y);
        }

    }


    @Test
    public void test(){

        String patten = "[WASD]\\d{1}\\?";
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()) {
            int x = 0;
            int y = 0;
            String str = sc.nextLine();
            String[] commonds = str.split(";");
            for (int i = 0; i < commonds.length; i++) {
                if (commonds[i].matches(patten)) {
                    if(commonds[i].startsWith("W")) {
                        y += Integer.parseInt(commonds[i].substring(1));
                    } else if(commonds[i].startsWith("S")) {
                        y -= Integer.parseInt(commonds[i].substring(1));
                    } else if(commonds[i].startsWith("A")) {
                        x -= Integer.parseInt(commonds[i].substring(1));
                    } else {
                        x += Integer.parseInt(commonds[i].substring(1));
                    }
                }
            }
            System.out.println(x + "," + y);
        }

    }

    @Test
    public void test3() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(() -> {
                int a = 1 / 0;
        });
        System.out.println("1");
        Thread.sleep(1000000000);
    }
}
