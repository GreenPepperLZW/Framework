package com.lzw.service;

import org.springframework.stereotype.Service;

/**
 * @author : lzw
 * @date : 2022/10/26
 * @since : 1.0
 */
@Service
public class HelloService {

    public String sayHello(String name) {
        return "hello" + name;
    }
}
