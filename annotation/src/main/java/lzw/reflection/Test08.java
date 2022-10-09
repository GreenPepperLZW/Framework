package lzw.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 使用反射获取类的运行时结构
 *
 * @author : lzw
 * @date : 2022/4/27
 * @since : 1.0
 */
public class Test08 {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException {
        Class c1 = Class.forName("lzw.reflection.User");

        // 获取类的名字 包名 + 类名
        System.out.println("包名 + 类名" + c1.getName());
        // 简单类名
        System.out.println("类名" + c1.getSimpleName());

        // 获取类的public属性
        Field[] fields = c1.getFields();
        System.out.println("public属性");
        for (Field field : fields) {
            System.out.println(field);
        }

        // 获取所有属性
        System.out.println("所有属性");
        fields = c1.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field);
            field.setAccessible(true);
            System.out.println("=========" + field.get(c1));

        }

        // 获取本类及其父类的public方法
        Method[] methods = c1.getMethods();
        System.out.println("public方法");
        for (Method method : methods) {
            System.out.println(method);
        }
        // 获取本类的所有方法
        methods = c1.getDeclaredMethods();
        System.out.println("所有方法");
        for (Method method : methods) {
            System.out.println(method);
        }

        System.out.println("============获取指定方法=====================");
        // 获取指定方法  方法名，方法参数class类型
        System.out.println(c1.getMethod("getName", null).getName());
        System.out.println(c1.getMethod("setName", String.class).getName());


        System.out.println("=============获取指定的构造器====================");

        // 获取指定的构造器
        Constructor[] constructors = c1.getConstructors();
        for (Constructor constructor : constructors) {
            System.out.println(constructor);
        }

        constructors = c1.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            System.out.println(constructor);
        }
        Constructor declaredConstructor = c1.getDeclaredConstructor(String.class, int.class, int.class);
        System.out.println("指定构造器" + declaredConstructor);


    }


}
