package com.lee.tx;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    UserDao userDao;


    public void insert(Integer id,String name){
        userDao.insert(id, name);
        delete(333);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Integer id) {
        userDao.delete(id);
        int a = 1 / 0;
    }
}
