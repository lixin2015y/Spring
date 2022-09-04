package com.compensate.enums;

public enum GapPolicy {

    /**
     * 补偿时间：
     * 1分钟内执行一次
     * 10分钟后再执行一次
     * 20分钟后再执行一次
     * 30分钟后执行一次
     */
    EQUAL_GAP_TIME("01","等值间隔时间策略"),

    /**
     * 补偿时间：
     * 1分钟内执行一次
     * 10分钟后执行一次
     * 30分钟后执行一次
     * 60分钟后执行一次
     */
    EQUAL_INCREASE_GAP_TIME("02","等增间隔时间策略"),

    ;

    private String code;
    private String desc;

    GapPolicy(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
