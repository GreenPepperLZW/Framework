package lzw.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : lzw
 * @date : 2022/4/13
 * @since : 1.0
 */
public class Test03 {

    /**
     * 注解中的参数如果没有默认值，必须赋值，否则报错
     */
    @MyAnnoation2(name = "张三",schools = {"清华"})
    @MyAnnoation3("aa")
    public void test() {
    }
}



/**
 * @author lzw
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnoation2 {

    /**
     * 注解的参数写法：参数类型 + 参数名()，还可以赋默认值
     * @return
     */
    String name() default "";

    int age() default 0;


    /**
     * 如果默认值为 -1 则代表不存在
     * @return
     */
    int id() default  -1;


    String[] schools() default {"北大"};
}

/**
 * @author lzw
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnoation3{

    /**
     * 只有一个值的注解，属性名建议用 value 命名,这样在使用注解的时候，可以直接赋值，而不需要 value = "lzw" 这样显示赋值
     * @return
     */
    String value();
}
