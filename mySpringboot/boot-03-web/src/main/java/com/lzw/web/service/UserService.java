package com.lzw.web.service;

import com.lzw.web.bean.UserDto;
import com.lzw.web.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * @author : lzw
 * @date : 2022/12/13
 * @since : 1.0
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public UserDto getUserDto(BigInteger id) {

        return userMapper.getUser(id);
    }

}
