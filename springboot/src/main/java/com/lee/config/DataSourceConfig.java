package com.lee.config;

import com.compensate.annotation.EnableCompensation;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@MapperScan("com.lee.dao")
@Configuration
@EnableCompensation
public class DataSourceConfig {

    @Bean("test")
    @ConfigurationProperties(prefix = "custom.datasource")
    public DataSource test() {
        return DataSourceBuilder.create().build();
    }

    @Bean("test1")
    @ConfigurationProperties(prefix = "custom.datasource.ds1")
    public DataSource test1() {
        return DataSourceBuilder.create().build();
    }


    @Bean("test2")
    @ConfigurationProperties(prefix = "custom.datasource.ds2")
    public DataSource test2() {
        return DataSourceBuilder.create().build();
    }

    @Bean("dynamicDataSource")
    public DynamicDataSource dynamicDataSource(@Autowired DataSource test,
                                               @Autowired DataSource test1,
                                               @Autowired DataSource test2) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(3);
        dataSourceMap.put("test", test);
        dataSourceMap.put("test1", test1);
        dataSourceMap.put("test2", test2);
        // 将 master 数据源作为默认指定的数据源
        dynamicDataSource.setDefaultDataSource(test);
        // 将 master 和 slave 数据源作为指定的数据源
        dynamicDataSource.setDataSources(dataSourceMap);
        return dynamicDataSource;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(@Autowired DynamicDataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        // 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource作为数据源则不能实现切换
        sessionFactory.setDataSource(dynamicDataSource);
//        sessionFactory.setTypeAliasesPackage("com.lee.dao");    // 扫描Model
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/*.xml"));
        return sessionFactory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Autowired DynamicDataSource dynamicDataSource) {
        // 配置事务管理, 使用事务时在方法头部添加@Transactional注解即可
        return new DataSourceTransactionManager(dynamicDataSource);
    }
}
