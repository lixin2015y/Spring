package com.compensate.dto;


import java.io.Serializable;
import java.util.UUID;

public class CompensationEntity implements Serializable {

    private static final long serialVersionUID = 3298185703974759143L;

    /**
     * 唯一标识
     */
    private String id;

    /**
     * 系统码
     */
    private String systemCode;

    /**
     * 补偿名
     */
    private String name;

    /**
     * 补偿码
     */
    private String compensationCode;

    /**
     * dubbo服务组名
     */
    private String group;

    /**
     * 错误标签
     */
    private String errorTag;

    /**
     * 错误信息
     */
    private String errorMessage;

    private Integer limit;

    private Integer logKeepDays;

    private Integer gapMinuter;

    private String gapPolicy;

    private String dataFormatDesc;

    /**
     * 补偿数据
     */
    private String data;

    public CompensationEntity() {
    }

    public CompensationEntity(String systemCode, String compensationCode, String name, String group, String errorTag, String errorMessage, Integer limit, Integer logKeepDays, Integer gapMinuter, String gapPolicy, String dataFormatDesc, String data) {
        this.id = UUID.randomUUID().toString();
        this.systemCode = systemCode;
        this.compensationCode = compensationCode;
        this.name = name;
        this.group = group;
        this.errorTag = errorTag;
        this.errorMessage = errorMessage;
        this.limit = limit;
        this.logKeepDays = logKeepDays;
        this.gapMinuter = gapMinuter;
        this.gapPolicy = gapPolicy;
        this.dataFormatDesc = dataFormatDesc;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getCompensationCode() {
        return compensationCode;
    }

    public void setCompensationCode(String compensationCode) {
        this.compensationCode = compensationCode;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getGapMinuter() {
        return gapMinuter;
    }

    public void setGapMinuter(Integer gapMinuter) {
        this.gapMinuter = gapMinuter;
    }

    public String getGapPolicy() {
        return gapPolicy;
    }

    public void setGapPolicy(String gapPolicy) {
        this.gapPolicy = gapPolicy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataFormatDesc() {
        return dataFormatDesc;
    }

    public void setDataFormatDesc(String dataFormatDesc) {
        this.dataFormatDesc = dataFormatDesc;
    }

    @Override
    public String toString() {
        return "CompensationEntity{" +
                "id='" + id + '\'' +
                ", systemCode='" + systemCode + '\'' +
                ", name='" + name + '\'' +
                ", compensationCode='" + compensationCode + '\'' +
                ", group='" + group + '\'' +
                ", errorTag='" + errorTag + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", limit=" + limit +
                ", logKeepDays=" + logKeepDays +
                ", gapMinuter=" + gapMinuter +
                ", gapPolicy='" + gapPolicy + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
