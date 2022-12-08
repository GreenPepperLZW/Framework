package com.lzw.web.exception;

import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 框架中默认提供了四个异常处理解析器分别是：
 * {@link ExceptionHandlerExceptionResolver} 执行处理标注有 @ExceptionHandler 注解的的异常
 * {@link ResponseStatusExceptionResolver} 处理
 * 处理 标注有 @ResponseStatus 注解的异常
 * {@link DefaultHandlerExceptionResolver} 具体处理哪些异常，这个类中已经标注了，如：BindException，TypeMismatchException
 * <p>
 * {@link DefaultErrorAttributes} 此异常处理解析器只是将错误信息存放到request域中，在以上三个处理器都无法处理的时候，底层发送/error请求时，给这个请求使用
 * 如果抛出的异常以上三个处理器都无法处理，则框架底层会发送一个 /error的请求，最终由底层提供的
 * {@link BasicErrorController#errorHtml(HttpServletRequest, HttpServletResponse)} 或 {@link BasicErrorController#error(HttpServletRequest)}
 * 方法处理，最终返回一个白色的错误页面或一串json字符错误说明
 * <p>
 * <p>
 * <p>
 * 所有的异常在条件满足的情况下都会被以上四种异常处理解析器处理，也可以自己自定义异常处理解析器
 *
 * @author : lzw
 * @date : 2022/12/8
 * @since : 1.0
 */
// 赋予最高优先级
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
public class CustomerHandlerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler,
                                         Exception ex) {
        try {
            response.sendError(511, "我自定义的错误");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView();
    }
}
