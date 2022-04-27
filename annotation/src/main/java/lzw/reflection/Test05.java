package lzw.reflection;

/**
 * @author : lzw
 * @date : 2022/4/25
 * @since : 1.0
 */
public class Test05 {



    public static void main(String[] args) {
        A a = new A();
        System.out.println(A.m);
    }
}


class A {

    // 静态代码的放置顺序会影响最终的值
    static int m = 100;

    static {
        System.out.println("A类静态代码块初始化");
        m = 300;
    }

    public A() {
        System.out.println("A类的无参构造初始化");
    }
}
