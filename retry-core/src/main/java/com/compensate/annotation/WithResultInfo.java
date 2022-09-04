package com.compensate.annotation;

import java.io.Serializable;
import java.util.Set;

public class WithResultInfo implements Serializable {

    private static final long serialVersionUID = 4004155619037326804L;

    private Boolean open;

    private String key;

    private Set<String> valueSet;


    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Set<String> getValueSet() {
        return valueSet;
    }

    public void setValueSet(Set<String> valueSet) {
        this.valueSet = valueSet;
    }

    @Override
    public String toString() {
        return "WithResultInfo{" +
                "open=" + open +
                ", key='" + key + '\'' +
                ", valueSet=" + valueSet +
                '}';
    }
}
