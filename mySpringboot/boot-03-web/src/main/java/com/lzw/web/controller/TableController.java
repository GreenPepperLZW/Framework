package com.lzw.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String dynamicTable() {

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
}

