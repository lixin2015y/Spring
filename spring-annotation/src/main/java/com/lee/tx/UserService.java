package com.lee.tx;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    UserDao userDao;

    @Transactional
    public void insert(String id,String name){
        userDao.insert(id, name);
        int a = 1 / 0;
    }
}
