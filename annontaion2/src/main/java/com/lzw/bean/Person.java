package com.lzw.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author : lzw
 * @date : 2022/5/5
 * @since : 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person {

    private String name;

    private int age;
}
