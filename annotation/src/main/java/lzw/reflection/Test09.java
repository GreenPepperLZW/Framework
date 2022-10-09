package lzw.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 通过反射动态的创建对象
 *
 * @author : lzw
 * @date : 2022/4/30
 * @since : 1.0
 */
public class Test09 {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Class c1 = Class.forName("lzw.reflection.User");
        // 本质上是调用了无参构造器
        User user = (User) c1.newInstance();

        System.out.println(user);

        // 通过构造器创建对象
        Constructor constructor = c1.getDeclaredConstructor();
        User user2 = (User) constructor.newInstance();
        System.out.println(user2);

        // 通过反射调用普通方法
        User user3 = (User) constructor.newInstance();
        // 获取方法
        Method setName = c1.getDeclaredMethod("setName", String.class);
        // 通过invoke调用方法，参数：对象，方法的参数
        setName.invoke(user3, "张三");
        System.out.println(user3.getName());

        // 通过反射操作属性
        User user4 = (User) constructor.newInstance();
        Field name = c1.getDeclaredField("name");
        // 不能直接操作私有属性或私有方法，关闭安全检测
        name.setAccessible(true);
        name.set(user4, "张三");
        System.out.println("name属性值" + user4.getName());


    }
}
