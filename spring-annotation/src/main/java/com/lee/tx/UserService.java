package com.lee.tx;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Resource
    UserDao userDao;

    @Resource
    UserService userService;


    @Transactional
    public void insert(Integer id,String name){
        userDao.insert(id, name);
        userService.delete(333);
        int a = 1 / 0;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Integer id) {
        userDao.insert(id + 1, "-----");
        List<Map<String, Object>> maps = userDao.selectAll();
        System.out.println("maps = " + maps);
    }
}
