package com.spi.service.impl;


import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.spi.service.Animal;
import com.spi.service.Color;

public class Dog implements Animal {

    /**
     * 加载扩展点时，自动注入依赖的扩展点。加载扩展点时，扩展点实现类的成员如果为其它扩展点类型，ExtensionLoader 在会自动注入依赖的扩展点。
     * ExtensionLoader 通过扫描扩展点实现类的所有 setter 方法来判定其成员。即 ExtensionLoader 会执行扩展点的拼装操作。
     *
     */
    // 动态生成的 SPI 适配类
    /*
     *
    public class Book$Adaptive implements io.study.dubbo.spi.ioc.spi.Book {
        @Override
        public java.lang.String bookName(com.alibaba.dubbo.common.URL arg0) {
            if (arg0 == null) {
                throw new IllegalArgumentException("url == null");
            }
            com.alibaba.dubbo.common.URL url = arg0;
            String extName = url.getParameter("language", "java");
            if (extName == null) {
                throw new IllegalStateException("Fail to get extension(io.study.dubbo.spi.ioc.spi.Book) name from url(" + url.toString() + ") use keys([language])");
            }
            io.study.dubbo.spi.ioc.spi.Book extension = (io.study.dubbo.spi.ioc.spi.Book) ExtensionLoader.getExtensionLoader(io.study.dubbo.spi.ioc.spi.Book.class).getExtension(extName);
            return extension.bookName(arg0);
        }
    }
    */


    private Color red;


    @Override
    public void sayHello() {
        System.out.println("汪汪汪");
        if (red != null) {
            URL url = new URL("red", "10.211.55.5", 8080);
            URL newUrl = url.addParameter("color", "black");
            red.sayMyColor(newUrl);
        }
    }

    public void setRed(Color red) {
        this.red = red;
    }
}
