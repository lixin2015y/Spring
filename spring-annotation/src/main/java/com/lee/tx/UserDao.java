package com.lee.tx;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class UserDao {

    @Resource
    JdbcTemplate jdbcTemplate;

    public void insert(String id, String name){
        String sql = "insert into test(id,name) values(?,?)";
        jdbcTemplate.update(sql, id, name);
    }

}
