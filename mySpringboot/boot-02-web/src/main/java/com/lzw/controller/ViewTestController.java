package com.lzw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 测试跳转到thymeleaf视图
 *
 * @author : lzw
 * @date : 2022/11/28
 * @since : 1.0
 */
@Controller
public class ViewTestController {

    @GetMapping("/test01")
    public String test01(Model model) {
        // model中的数据会被放在请求域中 request.setAttribute("a","bb")
        model.addAttribute("msg", "");
        model.addAttribute("link", "https://www.baidu.com");
        return "success";
    }

}
