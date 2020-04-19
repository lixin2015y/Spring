package com.lee.handle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TestHandleMapping {


    @RequestMapping(value = "testHeadersAndParams", params = {"username", "password=22"}, headers = "Accept-Encoding")
    String testHeadersAndParams() {
        return "success";
    }

    @RequestMapping("/testPathVariable/{id}")
    String testPathVariable(@PathVariable String id) {
        System.out.println("id = " + id);
        return "success";
    }

    @RequestMapping("/order/{id}")
    String testGetMapping(@PathVariable String id) {
        System.out.println("id = " + id);
        System.out.println("testGetMapping");
        return "success";
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.POST)
    String testPostMapping(@PathVariable String id) {
        System.out.println("id = " + id);
        System.out.println("testPostMapping");

        return "success";
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.PUT)
    String testPutMapping(@PathVariable String id) {
        System.out.println("id = " + id);
        System.out.println("testPutMapping");
        return "redirect:success";
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.DELETE)
    String testDeleteMapping(@PathVariable String id) {
        System.out.println("id = " + id);
        System.out.println("testDeleteMapping");

        return "redirect:success";
    }
}
