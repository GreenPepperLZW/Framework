package com.lzw.web.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author : lzw
 * @date : 2022/12/8
 * @since : 1.0
 */

@Slf4j
// 单个 * 号是servlet的下发，双 * 是spring的写法
//@WebFilter(urlPatterns = {"/css/*", "/images/*"})
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("MyFilter初始化完成");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("MyFilter工作中");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("MyFilter销毁");
    }
}
