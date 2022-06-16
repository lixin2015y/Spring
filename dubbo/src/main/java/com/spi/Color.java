package com.spi;

import com.alibaba.dubbo.common.extension.SPI;
import org.omg.CORBA.PRIVATE_MEMBER;

@SPI
public interface Color {

    void sayMyColor();
}
