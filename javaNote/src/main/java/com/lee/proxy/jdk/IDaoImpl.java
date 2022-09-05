package com.lee.proxy.jdk;

public class IDaoImpl implements IDao{
    @Override
    public String select() {
        select2();
        return "hello li xin";
    }

    @Override
    public String select2() {
        return null;
    }
}
