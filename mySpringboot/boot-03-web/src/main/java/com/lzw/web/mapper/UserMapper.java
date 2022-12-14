package com.lzw.web.mapper;

import com.lzw.web.bean.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;

/**
 * @author : lzw
 * @date : 2022/12/13
 * @since : 1.0
 */
@Mapper
public interface UserMapper {

    UserDto getUser(BigInteger id);
}
