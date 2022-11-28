package com.lzw.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : lzw
 * @date : 2022/11/28
 * @since : 1.0
 */
@Controller
public class IndexController {

    @GetMapping(value = {"/", "/login"})
    public String toLoginPage() {

        return "login";
    }
}
