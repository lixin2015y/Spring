package com.lee.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {


    @Bean(name = "ds1")
    @ConfigurationProperties(prefix = "custom.datasource.ds1")
    DruidDataSource dataSource1() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "ds2")
    @ConfigurationProperties(prefix = "custom.datasource.ds2")
    DruidDataSource dataSource2() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "ds3")
    @ConfigurationProperties(prefix = "custom.datasource.ds3")
    DruidDataSource dataSource3() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "dynamicDataSource")
    public DynamicDataSource DataSource(@Qualifier("ds1") DruidDataSource ds1,
                                        @Qualifier("ds2") DruidDataSource ds2) {

        //这个地方是比较核心的targetDataSource 集合是我们数据库和名字之间的映射
        Map<Object, Object> targetDataSource = new HashMap();
        targetDataSource.put(DataSourceType.DataBaseType.Ds1, ds1);
        targetDataSource.put(DataSourceType.DataBaseType.Ds2, ds2);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSource);
        //设置默认对象
        dataSource.setDefaultTargetDataSource(ds1);
        return dataSource;
    }


}
