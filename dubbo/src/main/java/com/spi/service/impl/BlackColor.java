package com.spi.service.impl;


import com.alibaba.dubbo.common.URL;
import com.spi.service.Color;


public class BlackColor implements Color {
    @Override
    public void sayMyColor(URL url) {
        System.out.println("黑色的：");
    }
}
