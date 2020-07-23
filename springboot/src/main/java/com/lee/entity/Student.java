package com.lee.entity;

import java.io.Serializable;

/**
 * (Student)实体类
 *
 * @author makejava
 * @since 2020-07-23 09:48:24
 */
public class Student implements Serializable {
    private static final long serialVersionUID = -77458509972832145L;
    
    private Integer sid;
    
    private String sname;
    
    private Integer sage;


    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Integer getSage() {
        return sage;
    }

    public void setSage(Integer sage) {
        this.sage = sage;
    }

}