package com.compensate.annotation;

import org.springframework.util.ConcurrentReferenceHashMap;

import java.io.Serializable;
import java.util.Arrays;

public class WithExceptionInfo implements Serializable {

    private static final long serialVersionUID = 2282964815564302821L;

    private Boolean open;

    private Class<? extends Throwable>[] value;

    private Class<? extends Throwable>[] includes;

    private Class<? extends Throwable>[] excludes;

    private Boolean throwExceptionTag;

    private ConcurrentReferenceHashMap<Class<? extends Throwable>,Boolean> cache;

    public WithExceptionInfo() {
        this.cache = new ConcurrentReferenceHashMap<>();
    }

    public ConcurrentReferenceHashMap getCache() {
        return cache;
    }

    public void setCache(ConcurrentReferenceHashMap cache) {
        this.cache = cache;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Class<? extends Throwable>[] getValue() {
        return value;
    }

    public void setValue(Class<? extends Throwable>[] value) {
        this.value = value;
    }

    public Class<? extends Throwable>[] getIncludes() {
        return includes;
    }

    public void setIncludes(Class<? extends Throwable>[] includes) {
        this.includes = includes;
    }

    public Class<? extends Throwable>[] getExcludes() {
        return excludes;
    }

    public void setExcludes(Class<? extends Throwable>[] excludes) {
        this.excludes = excludes;
    }

    public Boolean getThrowExceptionTag() {
        return throwExceptionTag;
    }

    public void setThrowExceptionTag(Boolean throwExceptionTag) {
        this.throwExceptionTag = throwExceptionTag;
    }


    @Override
    public String toString() {
        return "WithExceptionInfo{" +
                "open=" + open +
                ", value=" + Arrays.toString(value) +
                ", includes=" + Arrays.toString(includes) +
                ", excludes=" + Arrays.toString(excludes) +
                ", throwExceptionTag=" + throwExceptionTag +
                '}';
    }
}
