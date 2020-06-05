package com.lee.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {


    @Override
    protected Object determineCurrentLookupKey() {

        // 获取枚举类型对象
        DataSourceType.DataBaseType dataBaseType = DataSourceType.getDataBaseType();

        return dataBaseType;
    }
}
