package com.lzw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : lzw
 * @date : 2022/11/18
 * @since : 1.0
 */

@Controller
public class RequestController {

    @GetMapping("/goto")
    public String goToPage(HttpServletRequest request) {
        request.setAttribute("msg", "成功了。。。");
        request.setAttribute("code", 200);
        return "forward:success";
    }

    /**
     * 请求参数中使用 map，model接收数据时，解析参数时会将其中的数据存放到request域中
     *
     * @param map
     * @param model
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/params")
    public String testParam(Map<String, Object> map,
                            Model model,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        map.put("hello", "world555");
        model.addAttribute("world", "hello555");
        request.setAttribute("message", "hello world");
        Cookie cookie = new Cookie("c1", "v1");
        cookie.setDomain("localhost");
        response.addCookie(cookie);
        return "forward:/success";
    }

    /**
     * @param msg
     * @param code
     * @param request
     * @return
     * @RequestAttribute 获取request请求域中的注解
     */
    @ResponseBody
    @GetMapping("/success")
    public Map success(@RequestAttribute(value = "msg", required = false) String msg,
                       @RequestAttribute(value = "code", required = false) Integer code,
                       HttpServletRequest request) {
        Object msg1 = request.getAttribute("msg");

        //获取从/params跳转过来携带的参数
        Object hello = request.getAttribute("hello");
        Object world = request.getAttribute("world");
        Object message = request.getAttribute("message");

        Map map = new HashMap();
        map.put("msg", msg);
        map.put("msg1", msg1);
        map.put("code", code);

        map.put("hello", hello);
        map.put("world", world);
        map.put("message", message);
        return map;
    }

}
