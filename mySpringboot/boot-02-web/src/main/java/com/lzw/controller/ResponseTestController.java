package com.lzw.controller;

import com.lzw.bean.Person;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.accept.AbstractMappingContentNegotiationStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * @author : lzw
 * @date : 2022/11/25
 * @since : 1.0
 */
@Controller
public class ResponseTestController {


    /**
     * 探究返回值处理的原理：
     * 关键方法
     * {@link HandlerMethodReturnValueHandlerComposite#handleReturnValue(Object, MethodParameter, ModelAndViewContainer, NativeWebRequest)}
     * 具体的handlerMapping处理完成之后开始执行handleReturnValue方法处理返回值,spring提供了15个返回值处理器，其中
     * {@link RequestResponseBodyMethodProcessor} 支持处理标有  @ResponseBody 注解的返回类型。
     * {@link RequestResponseBodyMethodProcessor} 方法中会调用spring默认提供的10个消息转换器（HttpMessageConverter）来解析返回值，其中
     * {@link MappingJackson2HttpMessageConverter} 支持返回json格式的数据，原始的请求头中会在Accept中标注希望服务器返回哪种格式的数据，MappingJackson2HttpMessageConverter 转换器则刚好支持返回json数据
     * <p>
     * <p>
     * 内容协商原理
     * ：内容协商即为客户端与服务端相互协商好数据交互的类型，客户端可以在请求头中的Accept参数中设置希望返回的数据格式类型或者在请求参数 format 参数指定json或xml数据格式，服务端会将自己支持返回的所有数据格式与
     * 客户端希望要的数据格式进行遍历，最终确定要返回什么样的格式，然后再找到能解析这种格式的消息处理器，对参数进行处理
     * <p>
     * 内容协商策略管理器接口：{@link AbstractMappingContentNegotiationStrategy}
     * 寻找客户端需要什么格式的数据格式方法：{@link AbstractMessageConverterMethodProcessor#getAcceptableMediaTypes(HttpServletRequest)}
     * 统计自己能返回哪些格式的数据的方法：{@link AbstractMessageConverterMethodProcessor#getProducibleMediaTypes(HttpServletRequest, Class, Type)}
     *
     * @return 自定义消息转换器，希望达到的功能：
     * <p>
     * 1.浏览器发请求直接返回xml，浏览器发送请求时，xml的权重最高
     * 2.如果是Ajax请求，返回json
     * 3.如果是app发送请求，返回自定义协议数据
     * <p>
     * 步骤：
     * 1.添加自定义的MessageConverter进系统底层
     * 2.系统底层会统计出所有MessageConverter能操作哪些数据类型
     * 3.进行客户端内容协商
     */
    @ResponseBody
    @GetMapping("test/person")
    public Person getPerson() {
        Person person = new Person();
        person.setAge(28);
        person.setUserName("zhangsan");
        person.setBirth(new Date());
        return person;
    }

}
