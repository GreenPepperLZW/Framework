package com.lzw.controller;

import com.lzw.bean.Person;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : lzw
 * @date : 2022/11/18
 * @since : 1.0
 */
@RestController
public class ParameterTestController {


    /**
     * 自定义参数绑定原理
     * 关键步骤
     *
     * {@link ConversionUtils#invokeConverter(GenericConverter, Object, TypeDescriptor, TypeDescriptor)} 获取转换器
     *
     * @param person
     * @return
     */
    @PostMapping("/saveuser")
    public Person saveUser(Person person) {
        return person;
    }

    /**
     * @param id
     * @param name
     * @param pv
     * @return #
     * 各个注解作用说明：
     * @PathVariable 注解使用，获取请求路径上{} 中的参数,使用@PathVariable Map<String, String> pv可以获取请求路径上携带的所有请求参数
     * @RequestHeader 获取请求头信息，@RequestHeader Map<String, String> header，map类型形参可以获取所有请求头信息
     * @CookieValue 获取cookie的值,@CookieValue("XXL_JOB_LOGIN_IDENTITY") Cookie cookie这种方式可以获取cookie的键值对
     * <p>
     * 参数解析执行过程：
     * {@link DispatcherServlet#doDispatch(HttpServletRequest, HttpServletResponse)} ->
     * {@link AbstractHandlerMethodAdapter#handle(HttpServletRequest, HttpServletResponse, Object)} ->
     * {@link RequestMappingHandlerAdapter#handleInternal(HttpServletRequest, HttpServletResponse, HandlerMethod)} ->
     * {@link RequestMappingHandlerAdapter#invokeHandlerMethod(HttpServletRequest, HttpServletResponse, HandlerMethod)} ->
     * {@link ServletInvocableHandlerMethod#invokeAndHandle(ServletWebRequest, ModelAndViewContainer, Object...)} ->
     * {@link InvocableHandlerMethod#invokeForRequest(NativeWebRequest, ModelAndViewContainer, Object...)} ->
     * {@link InvocableHandlerMethod#getMethodArgumentValues(NativeWebRequest, ModelAndViewContainer, Object...)} ->
     * {@link HandlerMethodArgumentResolverComposite#resolveArgument(MethodParameter, ModelAndViewContainer, NativeWebRequest, WebDataBinderFactory)} ->
     * {@link HandlerMethodArgumentResolverComposite#getArgumentResolver(MethodParameter)}
     * 在 getArgumentResolver 方法中会遍历循环27个参数解析器，如果参数中存在相应的注解，则会返回对应的参数解析器
     * {@link AbstractNamedValueMethodArgumentResolver#resolveArgument(MethodParameter, ModelAndViewContainer, NativeWebRequest, WebDataBinderFactory)}
     * 在resolveArgument方法中进行对参数进行解析
     */
    @GetMapping("/car/{id}/owner/{name}")
    public Map<String, Object> getCar(@PathVariable("id") Integer id,
                                      @PathVariable("name") String name,
                                      @PathVariable Map<String, String> pv,
                                      @RequestHeader("User-Agent") String userAgetn,
                                      @RequestHeader Map<String, String> header,
                                      @RequestParam Integer age,
                                      @RequestParam List<String> inters,
                                      @RequestParam Map<String, String> params,
                                      @CookieValue("XXL_JOB_LOGIN_IDENTITY") String _ga,
                                      @CookieValue("XXL_JOB_LOGIN_IDENTITY") Cookie cookie) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("pv", pv);
        map.put("userAgetn", userAgetn);
        map.put("headers", header);
        map.put("age", age);
        map.put("inters", inters);
        map.put("params", params);
        map.put("XXL_JOB_LOGIN_IDENTITY", _ga);
        map.put("cookie", cookie);

        return map;
    }

    /**
     * @param content
     * @return
     * @RequestBody 接收post请求方式中的请求体参数
     */
    @PostMapping("/save")
    public Map post(@RequestBody String content) {
        Map map = new HashMap();
        map.put("content", content);
        return map;
    }

    /**
     * 测试请求地址中矩阵变量的获取
     *
     * @return
     */
    // 请求地址格式：cars/sell;low=34;brand=byd,audi
    // springMvc默认禁用矩阵变量，需要手动开启
    @GetMapping("/cars/sell")
    public Map carsCell(@MatrixVariable Integer low,
                        @MatrixVariable List<String> brand) {
        Map<String, Object> map = new HashMap<>();
        map.put("low", low);
        map.put("brand", brand);
        return map;
    }


    /**
     * 请求路径中携带的矩阵变量名字一样时，获取参数
     * /boss/1;age=20/2;age=10
     *
     * @param bossAge
     * @param empAge
     * @return
     */
    @GetMapping("/boss/{bossId}/{empId}")
    public Map boss(@MatrixVariable(value = "age", pathVar = "bossId") Integer bossAge,
                    @MatrixVariable(value = "age", pathVar = "empId") Integer empAge) {
        Map map = new HashMap();
        map.put("bossAge", bossAge);
        map.put("empAge", empAge);
        return map;
    }
}
