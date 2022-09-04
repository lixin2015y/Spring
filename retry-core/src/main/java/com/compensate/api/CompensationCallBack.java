package com.compensate.api;


import com.compensate.supports.CallBackResponse;

public interface CompensationCallBack {

    CallBackResponse callBack(String data);
}
