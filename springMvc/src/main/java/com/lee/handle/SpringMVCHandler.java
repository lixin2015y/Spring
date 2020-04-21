package com.lee.handle;

import com.lee.bean.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

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
    String testCookieValue(@CookieValue("JSESSIONID") String serssionId) {
        System.out.println("serssionId = " + serssionId);
        return "success";
    }

    @RequestMapping("testPojo")
    String testPojo(User user) {
        System.out.println("user = " + user);
        return "success";
    }

    @RequestMapping("testModalAndView")
    public ModelAndView testModalAndView(String userName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userName", userName);
        modelAndView.setViewName("success");
        return modelAndView;
    }

    @RequestMapping("download")
    ResponseEntity<byte[]> download(HttpServletRequest request) throws IOException {
        byte[] imgByte;
        final ServletContext servletContext = request.getSession().getServletContext();
        final InputStream in = servletContext.getResourceAsStream("images/a.jpg");

        imgByte = new byte[in.available()];
        in.read(imgByte);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=a.jpg");
        HttpStatus httpStatus = HttpStatus.OK;

        ResponseEntity<byte[]> responseEntity = new ResponseEntity(imgByte, headers, httpStatus);
        return responseEntity;
    }


    @PostMapping("upload")
    String upload(@RequestParam String desc, @RequestParam("uploadFile") MultipartFile file) {
        return "success";
    }
}
