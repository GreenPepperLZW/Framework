package com.lzw.web.bean;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author : lzw
 * @date : 2022/12/13
 * @since : 1.0
 */
@Data
public class UserDto {

    private BigInteger id;
    private String name;
    private int age;
    private String email;
    private Date createTime;
}
