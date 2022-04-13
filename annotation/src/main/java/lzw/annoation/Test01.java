package lzw.annoation;

/**
 *
 * 三种常见的注解
 *
 * @author : lzw
 * @date : 2022/4/12
 * @since : 1.0
 */
public class Test01 extends Object{

    /**
     * Override
     * 表示方法声明旨在覆盖超类型中的方法声明。 如果使用此注释类型注释方法，则除非至少满足以下条件之一，否则需要编译器生成错误消息：
     * 该方法将覆盖或实现在超类型中声明的方法。
     * 该方法具有与Object中声明的任何公共方法的覆盖相同的签名 。
     * @return
     */
    @Override
    public String toString() {
        return super.toString();
    }


    /**
     * 被 Deprecated 注释的程序元素是不鼓励使用的程序元素，通常它是危险的，或有更好的替代方法
     */
    @Deprecated
    public static void test() {

    }

    /**
     * 镇压警告
     */
    @SuppressWarnings("all")
    public void test02() {

    }


    public static void main(String[] args) {
        test();
    }

}
