package com.lzw.web.controller;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * 测试文件上传
 *
 * @author : lzw
 * @date : 2022/12/6
 * @since : 1.0
 */
public class FormTestController {

    @GetMapping("/form_layouts")
    public String formlayouts() {

        return "form/form_layouts";
    }
}
