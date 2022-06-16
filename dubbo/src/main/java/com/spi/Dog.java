package com.spi;


import org.omg.CORBA.PRIVATE_MEMBER;

public class Dog implements Animal {

    /**
     * 加载扩展点时，自动注入依赖的扩展点。加载扩展点时，扩展点实现类的成员如果为其它扩展点类型，ExtensionLoader 在会自动注入依赖的扩展点。
     * ExtensionLoader 通过扫描扩展点实现类的所有 setter 方法来判定其成员。即 ExtensionLoader 会执行扩展点的拼装操作。
     */
    private Color red;


    @Override
    public void sayHello() {
        System.out.println("汪汪汪");
        if (red != null) {
            red.sayMyColor();
        }
    }

    public void setRed(Color red) {
        this.red = red;
    }
}
