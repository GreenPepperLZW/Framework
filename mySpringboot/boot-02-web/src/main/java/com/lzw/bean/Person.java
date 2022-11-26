package com.lzw.bean;


import lombok.Data;

import java.util.Date;

/**
 * @author : lzw
 * @date : 2022/11/23
 * @since : 1.0
 */

@Data
public class Person {
    private String userName;
    private Integer age;
    private Date birth;
    private Pet pet;
}
