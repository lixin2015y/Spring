package com.spring.datasource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Test1 {

    ConfigurableApplicationContext context;


    @Before
    public void test1() {
        context = new ClassPathXmlApplicationContext("spring_datasource.xml");
    }


    @Test
    public void test2() throws SQLException {
        final DataSource dataSource = context.getBean("dataSource", DataSource.class);
        final Connection connection = dataSource.getConnection();
    }

    @Test
    public void test3() throws SQLException {
        final DataSource dataSource = context.getBean("dataSource2", DataSource.class);
        final Connection connection = dataSource.getConnection();
    }
}
