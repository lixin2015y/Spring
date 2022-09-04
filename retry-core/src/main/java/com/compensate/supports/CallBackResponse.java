package com.compensate.supports;

import java.io.Serializable;

public class CallBackResponse implements Serializable {

    private static final long serialVersionUID = -7982171837970777187L;

    private Boolean result;

    private String errorMsg;

    public CallBackResponse() {
    }

    public CallBackResponse(Boolean result, String errorMsg) {
        this.result = result;
        this.errorMsg = errorMsg;
    }

    public CallBackResponse(Boolean result) {
        this.result = result;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static CallBackResponse success(){
       return new CallBackResponse(true);
    }

    public static CallBackResponse fail(String errorMsg){
        return new CallBackResponse(false,errorMsg);
    }
}
