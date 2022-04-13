package lzw.annoation;

import java.lang.annotation.*;

/**
 * 测试元注解
 * Target、Retention、Documented、Inherited
 * <p>
 * 使用元注解自定义注解
 *
 * @author : lzw
 * @date : 2022/4/12
 * @since : 1.0
 */
public class Test02 {

}

// Target 表示注解可以用在哪些地方，TYPE 作用在类上， FIELD 属性上，METHOD方法上，PARAMETER参数上，CONSTRUCTOR构造方法上扽等
@Target(value = ElementType.METHOD)

// Retention 表示注解在什么地方还有效，SOURCE 代码中有效，CLASS 字节码文件中有效，RUNTIME 运行时有效
@Retention(value = RetentionPolicy.SOURCE)

// 表示是否将注解生成在JavaDoc中
@Documented

// 表示子类可以继承父类的注解
@Inherited
@interface myAnnoation {

}
