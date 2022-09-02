package com.lee.algorithm;

import com.lee.algorithm.utils.ArrayUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

@Slf4j
public class BaseTestApplication {

    public long startTime;
    public long endTime;

    @Before
    public void before() {
        startTime = System.currentTimeMillis();
    }

    @After
    public void after() {
        endTime = System.currentTimeMillis();
        log.info("方法执行耗时，{}秒", (endTime - startTime) / 1000);
    }


    @Test
    public void test2(){
        int[] arr = new int[]{-2, 2, 0, 3, 0, -2, 1, 6, 0, 0, 2, 0};
        int low = 0;
        int height = arr.length - 1;
        int mid = 0;
        while (mid <= height) {
            if (arr[mid] == 0) {
                mid++;
            } else if (arr[mid] < 0) {
                ArrayUtils.swap(arr, mid, low);
                low++;
                mid++;
            } else {
                ArrayUtils.swap(arr, mid, height);
                height--;
            }
        }
        System.out.println("Arrays.toString(arr) = " + Arrays.toString(arr));
    }
}
