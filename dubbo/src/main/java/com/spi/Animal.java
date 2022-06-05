package com.spi;

import com.alibaba.dubbo.common.extension.SPI;

@SPI
public interface Animal {
    void sayHello();
}
