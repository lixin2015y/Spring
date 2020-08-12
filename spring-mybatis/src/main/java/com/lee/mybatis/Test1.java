package com.lee.mybatis;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.lee.dao.UserDao;
import com.lee.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class Test1 {

    @Test
    public void test() throws IOException {
        // 使用xml创建sqlSessionFactory
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 得到SqlSession
        try (SqlSession session = sqlSessionFactory.openSession()) {

            //            User user = session.selectOne("com.lee.dao.UserDao.selectUser", 1);

            // 使用更简洁的方式
            UserDao userDao = session.getMapper(UserDao.class);
            User user = userDao.selectUser(1);

            System.out.println(user);
        }


    }


    /**
     * 不使用xml进行构造sqlSessionFactory
     */
    @Test
    public void test2() throws Exception {
        HashMap<String, String> map = new HashMap();
        map.put("driver", "com.mysql.jdbc.Driver");
        map.put("url", "jdbc:mysql://localhost:3306/test");
        map.put("username", "root");
        map.put("password", "123456");
        DataSource dataSource = DruidDataSourceFactory.createDataSource(map);
        JdbcTransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(UserDao.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

    }
}
