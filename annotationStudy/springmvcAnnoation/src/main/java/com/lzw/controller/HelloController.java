package com.lzw.controller;

import com.lzw.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : lzw
 * @date : 2022/10/26
 * @since : 1.0
 */
@Controller("/mvc")
public class HelloController {

    @Autowired
    private HelloService helloService;

    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        return helloService.sayHello("小明");
    }

    // 返回jsp页面
    // /WEB-INF/views/success.jsp
    @RequestMapping("suc")
    public String success() {
        return "success";
    }
}
