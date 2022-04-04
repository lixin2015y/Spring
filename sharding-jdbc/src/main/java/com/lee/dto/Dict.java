package com.lee.dto;

import java.io.Serializable;

public class Dict implements Serializable {


    private long dictId;

    private String name;


    public long getDictId() {
        return dictId;
    }

    public void setDictId(long dictId) {
        this.dictId = dictId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
