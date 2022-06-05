package com.api.service;

import java.util.HashMap;
import java.util.Map;

public class ServiceContext {

    private static ThreadLocal<ServiceContext> context = ThreadLocal.withInitial(ServiceContext::new);

    private Map<String, String> headers = new HashMap<>();

    static public ServiceContext getContext() {
        return context.get();
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public Map<String, String> getCloneHeader() {
        return new HashMap<>(headers);
    }

    public void addHeaders(Map<String, String> map) {
        headers.putAll(map);
    }

    public String getName() {
        return headers.get("name");
    }
}
