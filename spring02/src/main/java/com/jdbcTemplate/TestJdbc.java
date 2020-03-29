package com.jdbcTemplate;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;

public class TestJdbc {

    JdbcTemplate jdbcTemplate;

    @Before
    public void get() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("JdbcTemplate.xml");
        jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
    }


    /**
     * update 增删改操作
     */
    @Test
    public void test() {
        String sql = "insert into car(id,`number`,`load`) value(?,?,?)";
        jdbcTemplate.update(sql, null, "天天", 123);
    }

    @Test
    public void test2() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[]{null, "天天", 123});
        list.add(new Object[]{null, "天天2", 123});
        list.add(new Object[]{null, "天天3", 123});
        String sql = "insert into car(id,`number`,`load`) value(?,?,?)";
        jdbcTemplate.batchUpdate(sql, list);
    }

    /**
     * queryForObject
     */
    @Test
    public void test4() {
        String sql = "select * from car where id = ?";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper(Car.class);
        final Car car = jdbcTemplate.queryForObject(sql, rowMapper, 3);
        System.out.println(car);
    }

    /**
     * 查询多条数据
     */
    @Test
    public void testQuery() {
        String sql = "select * from car";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper(Car.class);
        final List<Car> carList = jdbcTemplate.query(sql, rowMapper);
        System.out.println(carList);
    }
}
