package com.spi.service;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;
import org.omg.CORBA.PRIVATE_MEMBER;

@SPI
public interface Color {

    @Adaptive({"color"})
    void sayMyColor(URL url);
}
