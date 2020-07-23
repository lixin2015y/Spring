package com.lee.handle;

import com.lee.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class UserController {


    @Resource
    UserDao userDao;

    @ResponseBody
    @RequestMapping("getAllUser")
    List<User> getAllUser() {
        return userDao.getUserList();
    }

}
