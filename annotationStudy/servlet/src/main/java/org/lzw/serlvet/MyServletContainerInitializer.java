package org.lzw.serlvet;

import org.lzw.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.util.EnumSet;
import java.util.Set;

/**
 * @author : lzw
 * @date : 2022/10/18
 * @since : 1.0
 */
//容器启动的时候会将@HandlesTypes指定的这个类型下面的子类（实现类，子接口等）传递过来；
//传入感兴趣的类型；
@HandlesTypes(value = {UserService.class})
public class MyServletContainerInitializer implements ServletContainerInitializer {


    /**
     * 应用启动的时候，会运行onStartup方法；
     * <p>
     * Set<Class<?>> arg0：感兴趣的类型的所有子类型；
     * ServletContext arg1:代表当前Web应用的ServletContext；一个Web应用一个ServletContext；
     * <p>
     * 1）、使用ServletContext注册Web组件（Servlet、Filter、Listener）
     * 2）、使用编码的方式，在项目启动的时候给ServletContext里面添加组件；
     * 必须在项目启动的时候来添加；
     * 1）、ServletContainerInitializer得到的ServletContext；
     * 2）、ServletContextListener得到的ServletContext；
     */
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext sc) throws ServletException {
        System.out.println("感兴趣的类型：");
        for (Class<?> claz : c) {
            System.out.println(claz);
        }
        //注册组件  ServletRegistration
        ServletRegistration.Dynamic servlet = sc.addServlet("userServlet", new UserServlet());
        //配置servlet的映射信息
        servlet.addMapping("/user");

        //注册Listener
        sc.addListener(UserListener.class);

        //注册Filter  FilterRegistration
        FilterRegistration.Dynamic filter = sc.addFilter("userFilter", UserFilter.class);
        //配置Filter的映射信息
        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");


    }
}
