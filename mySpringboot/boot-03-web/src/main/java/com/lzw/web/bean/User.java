package com.lzw.web.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : lzw
 * @date : 2022/12/2
 * @since : 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String userName;
    private String password;
}
