package org.lzw.serlvet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 监听项目启停
 *
 * @author : lzw
 * @date : 2022/10/18
 * @since : 1.0
 */
public class UserListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("contextInitialized...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.printf("contextDestroyed...");
    }
}
