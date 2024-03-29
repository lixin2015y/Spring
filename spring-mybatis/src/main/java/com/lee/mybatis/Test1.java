package com.lee.mybatis;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
import java.util.List;

public class Test1 {

    @Test
    public void test() throws IOException {
        // 使用xml创建sqlSessionFactory
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 得到SqlSession
        try (SqlSession session = sqlSessionFactory.openSession();
             SqlSession session2 = sqlSessionFactory.openSession()) {

            // User user = session.selectOne("com.lee.dao.UserDao.selectUser", 1);

            // 使用接口的方式
            UserDao userDao = session.getMapper(UserDao.class);
            User user = userDao.selectUser(1);
            session.close();

            UserDao userDao2 = session2.getMapper(UserDao.class);
            User user2 = userDao2.selectUser(1);

            System.out.println(user == user2);
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


    @Test
    public void testPage() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        Page<Object> objects = PageHelper.startPage(1, 3);
        List<User> users = userDao.selectAllUser();
        System.out.println(objects.getTotal());
        System.out.println(objects.getPageNum());
        System.out.println(users.toString());
    }
}
