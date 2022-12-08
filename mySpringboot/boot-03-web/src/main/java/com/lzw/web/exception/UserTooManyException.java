package com.lzw.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author : lzw
 * @date : 2022/12/8
 * @since : 1.0
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "用户数量太多")
public class UserTooManyException extends RuntimeException {

    public UserTooManyException() {
    }

    public UserTooManyException(String message) {
        super(message);
    }
}
