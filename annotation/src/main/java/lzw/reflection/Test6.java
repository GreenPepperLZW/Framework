package lzw.reflection;

/**
 * 测试类什么时候会初始化
 *
 * @author : lzw
 * @date : 2022/4/25
 * @since : 1.0
 */
public class Test6 {
    static {
        System.out.println("Main类被加载");
    }

    public static void main(String[] args) throws ClassNotFoundException {
        // 1. 主动引用
        Son son  = new Son();

        // 通过反射产生主动引用
        Class.forName("lzw.reflection.Son");
        // 调用类的静态成员（除了final常量类）和静态方法
        // 初始化一个类，如果父类没有被初始化，则会先初始化他的父类


        /**
         * 类的被动引用，不会产生类的初始化
         *
         * 1.当访问一个静态域时，只有真正声明这个类的域的类才会被初始化。如：当通过子类引用父类的静态常量，不会导致子类初始化
         * 2.通过数组定义类引用，不会触发此类的初始化
         * 3.引用常量不会触发此类的初始化（常量在链接阶段就存入调用类的常量池中了）
         */

        // 1
        System.out.println(Son.b);

        //2
        Son [] sons = new Son[3];

        //3
        System.out.println(Son.M);

    }
}


class Father {
    static int b = 2;
    static {
        System.out.println("父类被加载");
    }
}

class Son extends Father {
    static {
        System.out.println("子类被加载");
        m = 300;
    }

    static int m  = 100;

    static final int M = 200;
}
