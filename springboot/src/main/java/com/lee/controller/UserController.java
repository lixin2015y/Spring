package com.lee.controller;

import com.lee.entity.User;
import com.lee.service.TestService;
import com.lee.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2020-07-23 09:42:44
 */
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public User selectOne(Integer id) {
        return this.userService.queryById(id);
    }


    @Resource
    TestService testService;

    @GetMapping("testService")
    public String testService() {
        testService.test("1");
        return "a";
    }

}