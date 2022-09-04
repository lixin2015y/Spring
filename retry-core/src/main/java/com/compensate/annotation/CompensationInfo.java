package com.compensate.annotation;



import com.compensate.enums.GapPolicy;

import java.io.Serializable;

public class CompensationInfo implements Serializable {

    private static final long serialVersionUID = -8440138414960315183L;

    private String code;

    private String name;

    private String systemCode;

    private String group;

    private WithExceptionInfo withExceptionInfo;

    private WithResultInfo withResultInfo;


    private Integer limit;

    private Integer logKeepDays;

    private String errorTag;

    private String errorMessage;

    private Integer gapMinuter;

    private GapPolicy gapPolicy;

    private Boolean open;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public WithExceptionInfo getWithExceptionInfo() {
        return withExceptionInfo;
    }

    public void setWithExceptionInfo(WithExceptionInfo withExceptionInfo) {
        this.withExceptionInfo = withExceptionInfo;
    }

    public WithResultInfo getWithResultInfo() {
        return withResultInfo;
    }

    public void setWithResultInfo(WithResultInfo withResultInfo) {
        this.withResultInfo = withResultInfo;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLogKeepDays() {
        return logKeepDays;
    }

    public void setLogKeepDays(Integer logKeepDays) {
        this.logKeepDays = logKeepDays;
    }

    public String getErrorTag() {
        return errorTag;
    }

    public void setErrorTag(String errorTag) {
        this.errorTag = errorTag;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Integer getGapMinuter() {
        return gapMinuter;
    }

    public void setGapMinuter(Integer gapMinuter) {
        this.gapMinuter = gapMinuter;
    }

    public GapPolicy getGapPolicy() {
        return gapPolicy;
    }

    public void setGapPolicy(GapPolicy gapPolicy) {
        this.gapPolicy = gapPolicy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CompensationInfo{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", systemCode='" + systemCode + '\'' +
                ", group='" + group + '\'' +
                ", withExceptionInfo=" + withExceptionInfo +
                ", withResultInfo=" + withResultInfo +
                ", limit=" + limit +
                ", logKeepDays=" + logKeepDays +
                ", errorTag='" + errorTag + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", gapMinuter=" + gapMinuter +
                ", gapPolicy=" + gapPolicy +
                ", open=" + open +
                '}';
    }
}
