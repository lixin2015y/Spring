package com.lee.dao;

import com.lee.bean.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    private static List<User> userList = new ArrayList<>();

    static {
        for (int i = 0; i < 10; i++) {
            userList.add(new User("name" + i, i + 10));
        }
    }

    public List<User> getUserList(){
        return userList;
    }

    public static void setUserList(List<User> userList) {
        UserDao.userList = userList;
    }
}
