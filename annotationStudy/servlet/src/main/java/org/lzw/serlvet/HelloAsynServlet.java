package org.lzw.serlvet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理异步请求
 *
 * @author : lzw
 * @date : 2022/10/28
 * @since : 1.0
 */
@WebServlet(value = "/async", asyncSupported = true)
public class HelloAsynServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 支持异步处理：asyncSupported = true
        // 开启异步模式
        AsyncContext startAsync = req.startAsync();
        System.out.println(Thread.currentThread() + "...start...");

        // 业务逻辑进行异步处理
        startAsync.start(() -> {
            try {
                sayHello();
                // 获取异步的上下文
                AsyncContext asyncContext = req.getAsyncContext();
                // 获取响应
                ServletResponse response = asyncContext.getResponse();
                response.getWriter().write("hello async...");
                // 声明异步调用结束
                startAsync.complete();
            } catch (Exception e) {
                System.out.println(e);
                throw new RuntimeException(e);
            }
        });
        System.out.println(Thread.currentThread() + "...end...");
    }

    public void sayHello() throws InterruptedException {
        System.out.println(Thread.currentThread() + "...processing...");
        Thread.sleep(3000);
    }
}
