package com.lee.aop;

import java.util.PriorityQueue;

/**
 * @className: Caculator
 * @author: li xin
 * @date: 2022-03-22
 **/
public interface Calculator {
    int div(int i, int j) throws ServiceException;

    int add(int i, int j);
}
