package lzw.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 测试性能问题
 * @author : lzw
 * @date : 2022/5/5
 * @since : 1.0
 */
public class Test10 {

    // 普通方式调用
    public static void test01() {
        User user = new User();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            user.getName();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("普通方法执行10亿次花费时间："+(endTime-startTime)+"毫秒");// 4毫秒

    }

    // 反射方式调用
    public static void test02() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        long startTime = System.currentTimeMillis();
        User user = new User();
        Class c1 = user.getClass();
        Method getName = c1.getDeclaredMethod("getName", null);
        for (int i = 0; i < 1000000000; i++) {
            getName.invoke(user,null);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("反射方式方法执行10亿次花费时间："+(endTime-startTime)+"毫秒"); // 2343毫秒
    }

    // 反射方式调用，关闭安全检测
    public static void test03() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        long startTime = System.currentTimeMillis();
        User user = new User();
        Class c1 = user.getClass();
        Method getName = c1.getDeclaredMethod("getName", null);
        // 关闭安全检测
        getName.setAccessible(true);
        for (int i = 0; i < 1000000000; i++) {
            getName.invoke(user,null);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("反射方式方法执行10亿次花费时间："+(endTime-startTime)+"毫秒"); // 1419毫秒
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
//        test01();
//        test02();
        test03();
    }
}
