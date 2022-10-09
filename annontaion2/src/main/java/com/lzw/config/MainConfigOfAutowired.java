package com.lzw.config;

import com.lzw.bean.BookDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 自动装配：
 * Spring利用依赖注入（DI），完成对IOC容器中各个组件的依赖关系赋值
 * <p>
 * 1)、@Autowired:自动注入
     * 1)、默认优先按照类型去容器中找对应的组件：applicationContext.getBean(BookService.class)
     * 2)、如果找到多个相同类型的组件，再将属性的名称作为组件的id去容器中查找
     * applicationContext.getBean("bookDao")
     * 3)、使用@Qualifier("bookDao2")指定需要装配的组件id，而不是使用属性名
     * 4)、自动装配默认一定要将属性赋值好，依赖的bean如果不存在就会报错
     * 5)、@Autowired(required = false),使用required = false来调整是否必须装配
     * 6)、@Primary：让Spring进行自动装配时，默认使用首选的bean,容器中存在两个bean,bookDao,bookDao2,如果再bookDao2上加上注解@Primary，
 *        则在自动装配时，没有明确指定的情况下，会首选装配bookDao2
 *
 *
 * @author : lzw
 * @date : 2022/10/9
 * @since : 1.0
 */
@Configuration
@ComponentScan({"com.lzw.bean"})
public class MainConfigOfAutowired {

    // 扫描进一个BookDao的bean，再手动注册一个BookDao的bean，看在BookService中会自动注入哪一个
    @Bean("bookDao2")
    // Spring自动装配时，首选bookDao2
    @Primary
    public BookDao bookDao() {
        BookDao bookDao = new BookDao();
        bookDao.setLabel(2);
        return bookDao;
    }
}
