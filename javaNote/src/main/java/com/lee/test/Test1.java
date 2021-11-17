package com.lee.test;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @className: Test1
 * @author: li xin
 * @date: 2021-11-03
 **/
public class Test1 {

    public int[] twoSum(int[] numbers, int target) {
        // 空间换时间
        Map<Integer, Integer> map = new HashMap<>(numbers.length * 2);
        Map<Integer, Integer> map2 = new HashMap<>(16);
        for (int i = 0; i < numbers.length; i++) {
            if (!map.containsKey(numbers[i])) {
                map.put(numbers[i], i);
            } else {
                map2.put(numbers[i], i);
            }
        }

        for (Integer i : map.keySet()) {
            if (i == 0) {
                if (map2.containsKey(0)) {
                    return new int[]{map.get(0), map2.get(0)};
                }
            }

            if (map.containsKey(target - i)) {
                return new int[]{map.get(i), map.get(target - i)};
            } else if (map2.containsKey(target - i)) {
                return new int[]{map.get(i), map2.get(target - i)};
            }


        }
        return null;

    }


    @Test
    public void test(){
        System.out.println("movingCount(11, 8, 16) = " + Arrays.toString(twoSum(new int[]{0, 0, 0, 0, 0}, 0)));
    }
}
