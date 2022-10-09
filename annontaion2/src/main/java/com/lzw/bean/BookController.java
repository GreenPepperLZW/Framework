package com.lzw.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author : lzw
 * @date : 2022/5/5
 * @since : 1.0
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    public void print() {
        System.out.println(bookService);
    }

}
