package com.lee.handle;

import com.lee.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpringMVCHandler {


    @RequestMapping("/hello")
    String hello() {
        System.out.println("hello springMvc");
        return "success";
    }

    @RequestMapping("testRequestHeader")
    String testRequestHeader(@RequestHeader("Accept-Language") String language) {
        System.out.println("language = " + language);
        return "success";
    }

    @RequestMapping("testCookieValue")
    String testCookieValue(@CookieValue("JSESSIONID")  String serssionId) {
        System.out.println("serssionId = " + serssionId);
        return "success";
    }

    @RequestMapping("testPojo")
    String testPojo(User user) {
        System.out.println("user = " + user);
        return "success";
    }

}
