package com.lee.entity;

import java.io.Serializable;

/**
 * (Teacher)实体类
 *
 * @author makejava
 * @since 2020-07-23 09:48:06
 */
public class Teacher implements Serializable {
    private static final long serialVersionUID = -52161772724329429L;
    
    private Integer tid;
    
    private String tname;
    
    private Integer tage;


    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Integer getTage() {
        return tage;
    }

    public void setTage(Integer tage) {
        this.tage = tage;
    }

}