package com.lee.config;

import com.lee.annotation.DataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class DynamicDataSourceAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Before("@annotation(dataSource)")
    public void changeDataSource(JoinPoint joinPoint, DataSource dataSource) throws ClassNotFoundException {

        final String value = dataSource.value();
        switch (value) {
            case "ds1":
                DataSourceType.setDataBaseType(DataSourceType.DataBaseType.Ds1);
                break;
            case "ds2":
                DataSourceType.setDataBaseType(DataSourceType.DataBaseType.Ds2);
                break;
            case "ds3":
                DataSourceType.setDataBaseType(DataSourceType.DataBaseType.Ds3);
                break;
            default:

        }


    }

}
