package com.lee.tx;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import sun.security.krb5.internal.PAData;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao {

    @Resource
    JdbcTemplate jdbcTemplate;

    public void insert(Integer id, String name){
        String sql = "insert into test(id,name) values(?,?)";
        jdbcTemplate.update(sql, id, name);
    }

    public void delete(Integer id) {
        String sql = "delete from test where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Map<String, Object>> selectAll() {
        List<Map<String, Object>> list = new ArrayList<>();
        jdbcTemplate.query("select * from test", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Map<String, Object> param = new HashMap<>();
                param.put("id", id);
                param.put("name", name);
                list.add(param);
            }
        });
        return list;
    }


}
