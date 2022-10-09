package com.lzw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    /*404 not found*/
    private Integer code;
    /*消息：success failure*/
    private String message;
    /*数据*/
    private T data;

    public CommonResult(Integer code, String message) {
        this(code, message, null);
    }

}
