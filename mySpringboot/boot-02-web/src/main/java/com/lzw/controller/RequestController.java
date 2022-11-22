package com.lzw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
     * @param msg
     * @param code
     * @param request
     * @return
     * @RequestAttribute 获取request请求域中的注解
     */
    @ResponseBody
    @GetMapping("/success")
    public Map success(@RequestAttribute("msg") String msg,
                       @RequestAttribute("code") Integer code,
                       HttpServletRequest request) {
        Object msg1 = request.getAttribute("msg");
        Map map = new HashMap();
        map.put("msg", msg);
        map.put("msg1", msg1);
        map.put("code", code);
        return map;
    }

}
