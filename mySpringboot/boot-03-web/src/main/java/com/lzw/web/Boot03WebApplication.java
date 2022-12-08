package com.lzw.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.lzw.web")
public class Boot03WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(Boot03WebApplication.class, args);
    }

}
