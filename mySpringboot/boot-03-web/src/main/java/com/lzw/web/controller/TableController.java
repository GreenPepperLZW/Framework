package com.lzw.web.controller;

import com.lzw.web.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

/**
 * 访问表格的处理器
 *
 * @author : lzw
 * @date : 2022/12/2
 * @since : 1.0
 */
@Controller
public class TableController {

    @GetMapping("/basic_table")
    public String basicTable() {

        return "table/basic_table";
    }

    @GetMapping("dynamic_table")
    public String dynamicTable(Model model) {
        //构造数据
        List<User> users = Arrays.asList(new User("zhangsan", "aa"),
                new User("lisi", "bbb"),
                new User("wangwu", "cccc"),
                new User("zhaoliu", "dddd"));
        model.addAttribute("users", users);
        return "table/dynamic_table";
    }

    @GetMapping("/responsive_table")
    public String responsiveTable() {

        return "table/responsive_table";
    }

    @GetMapping("/editable_table")
    public String editableTable() {

        return "table/editable_table";
    }

    @GetMapping("/form_layouts")
    public String formlayouts() {

        return "form/form_layouts";
    }
}

