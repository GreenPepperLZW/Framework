package org.lzw.serlvet;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author : lzw
 * @date : 2022/10/18
 * @since : 1.0
 */
public class UserFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 过滤请求
        System.out.println("UserFilter...");
        // 放行
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }
}
