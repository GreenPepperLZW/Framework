package org.lzw.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : lzw
 * @date : 2022/11/16
 * @since : 1.0
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello01() {
        return "hello,SpringBoot";
    }
}
