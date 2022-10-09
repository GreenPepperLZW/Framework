package com.lzw.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : lzw
 * @date : 2022/6/25
 * @since : 1.0
 */

@RestController
public class HelloController {


    @RequestMapping("hello")
    public String helloController() {
        System.out.printf("hello security");
        return "hello spring security";
    }


}
