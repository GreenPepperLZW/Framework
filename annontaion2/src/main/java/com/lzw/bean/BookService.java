package com.lzw.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : lzw
 * @date : 2022/5/5
 * @since : 1.0
 */
@Service
public class BookService {

    //    @Qualifier("bookDao2")
    @Autowired(required = false)
    private BookDao bookDao;

    public void print() {
        System.out.println("==bookDao==" + bookDao);
    }

    @Override
    public String toString() {
        return "BookService{" +
                "bookDao=" + bookDao +
                '}';
    }
}
