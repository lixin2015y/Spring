package com.lee.algorithm.dongtaiguihua;

import com.lee.algorithm.BaseTestApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 斐波那契数列
 */
@Slf4j
public class Problem1 extends BaseTestApplication {

    private static Map<Integer, Integer> memo = new HashMap<>();


    @Test
    public void test() {
//        System.out.println(solve(35)); // 60
//        System.out.println(solve2(1000)); // 2
        System.out.println(solve3(1000)); // 1
    }

    private static int solve(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        return solve(n - 1) + solve(n - 2);
    }

    /**
     * 空间换时间，将计算结果保存起来（记忆化搜索）
     *
     * @param n
     * @return
     */
    private static int solve2(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        if (!memo.containsKey(n)) {
            memo.put(n, solve2(n - 1) + solve2(n - 2));
        }
        return memo.get(n);
    }


    /**
     * 动态规划，自下而上的解决问题，
     *
     * @param n
     * @return
     */
    private static int solve3(int n) {
        memo.put(0, 0);
        memo.put(1, 1);
        for (int i = 2; i <= n; i++) {
            memo.put(i, memo.get(i - 1) + memo.get(i - 2));
        }
        return memo.get(n);
    }

    @Before
    public void before() {
        startTime = System.currentTimeMillis();
    }

    @After
    public void after() {
        endTime = System.currentTimeMillis();
        System.out.println(String.format("方法执行耗时，%d秒", (endTime - startTime)));
    }
}
