package com.lzw.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理整个web controller的异常
 *
 * @author : lzw
 * @date : 2022/12/8
 * @since : 1.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 处理数学计算异常与空指针异常
     * 异常被捕获后，整个处理流程继续往下执行，走到
     * {@link DispatcherServlet#processDispatchResult(HttpServletRequest, HttpServletResponse, HandlerExecutionChain, ModelAndView, Exception)}
     * 方法，将自定义异常处理器的返回值作为veiwName，构建ModelAndView，最终返回一个页面给前端
     *
     * @param e
     * @return
     */
    @ExceptionHandler({ArithmeticException.class, NullPointerException.class})
    public String handlerArithException(Exception e) {
        log.error("异常是：{}", e);
        return "login";
    }
}
