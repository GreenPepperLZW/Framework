package com.lzw.web.controller;

import com.lzw.web.bean.User;
import com.lzw.web.bean.UserDto;
import com.lzw.web.service.UserService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author : lzw
 * @date : 2022/11/28
 * @since : 1.0
 */
@Slf4j
@Controller
public class IndexController {

    /**
     * 指标监控注册器
     */
    Counter counter;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserService userService;

    public IndexController(MeterRegistry registry) {
        counter = registry.counter("login.count");
    }

    @ResponseBody
    @GetMapping("getUser")
    public UserDto getUserDto(@RequestParam("id") BigInteger id) {
        return userService.getUserDto(id);
    }

    @ResponseBody
    @GetMapping("testSql")
    public String testSql() {

        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from user");
        return maps.toString();
    }

    @GetMapping(value = {"/", "/login"})
    public String toLoginPage() {
        // 次数加1
        counter.increment();
        return "login";
    }


    @PostMapping("login")
    public String man(User user, HttpSession session, Model model) {
        if (!(StringUtils.isEmpty(user.getUserName())) && "a".equals(user.getPassword())) {
            // 把登录成功的用户保存起来
            session.setAttribute("loginUser", user);
            // 登录后重定向到主页面，防止跳转到主页面后请求仍是登录请求，刷新页面表单反复提交登录请求
            return "redirect:main.html";
        } else {
            model.addAttribute("msg", "账号密码错误");
            // 回到登录页
            return "login";
        }
    }

    @GetMapping("main.html")
    public String mainPage(HttpSession session, Model model) {
        log.info("当前执行的方法是：{}", "mainPage");

        /*// 判断是否登录
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null) {
            return "main";
        } else {
            model.addAttribute("msg", "请进行登录");
            // 回到登录页
            return "login";
        }*/
        return "main";
    }
}
