package com.lzw.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : lzw
 * @date : 2022/11/18
 * @since : 1.0
 */

@RestController
public class HelloController {

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUser() {
        return "get 张三";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String saveUser() {
        return "post 张三";
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public String putUser() {
        return "put 张三";
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public String deleteUser() {
        return "delete 张三";
    }

}
