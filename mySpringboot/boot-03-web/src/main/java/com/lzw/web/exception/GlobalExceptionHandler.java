package com.lzw.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

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
     * 方法，将自定义异常处理器的返回值作为veiwName，构建ModelAndView，底层会执行
     * {@link ResponseStatusExceptionResolver#applyStatusAndReason(int, String, HttpServletResponse)}
     * 方法中的
     * {@link HttpServletResponse#sendError(int)}方法跳到错误页
     * <p>
     * ========================================================================================================================
     * 标注 @ExceptionHandler 的bean 会被 {@link ExceptionHandlerExceptionResolver} 类处理，
     * springMvc默认提供了四个异常处理解析器，分别处理不同情况下的异常
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
