package com.lzw.web.controller;

import com.lzw.web.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @author : lzw
 * @date : 2022/11/28
 * @since : 1.0
 */
@Controller
public class IndexController {

    @GetMapping(value = {"/", "/login"})
    public String toLoginPage() {

        return "login";
    }


    @PostMapping("login")
    public String man(User user, HttpSession session, Model model) {
        if (!(StringUtils.isEmpty(user.getUserName())) && "123456".equals(user.getPassword())) {
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

        // 判断是否登录
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null) {
            return "main";
        } else {
            model.addAttribute("msg", "请进行登录");
            // 回到登录页
            return "login";
        }
    }
}
