package com.spi.service;

import com.alibaba.dubbo.common.extension.SPI;

@SPI
public interface Animal {
    void sayHello();
}
