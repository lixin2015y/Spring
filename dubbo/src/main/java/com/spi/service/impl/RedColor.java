package com.spi.service.impl;


import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Activate;
import com.spi.service.Color;

public class RedColor implements Color {
    @Override
    public void sayMyColor(URL url) {
        System.out.println("红色的：");
    }
}
