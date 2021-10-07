package com.lee.dao;

import com.lee.entity.User;

import java.util.List;

public interface UserDao {

    User selectUser(Integer id);

    List<User> selectAllUser();
}
