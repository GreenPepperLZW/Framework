package lzw.reflection;

import java.lang.reflect.*;
import java.util.List;
import java.util.Map;

/**
 * 通过反射获取泛型信息
 * <p>
 * java采用类型擦除的机制来引入泛型，Java中的泛型仅仅是给编译器javac使用的，确保数据的安全性和免去强制类型转换问题，但是
 * 一旦编译完成，所有和泛型有关的类型全部擦除
 * <p>
 * 为了通过反射操作这些类型，Java新增了 ParameterizedType,GenericArrayType,TypeVariable,WildcardType 几种类型
 * 来代表不能被归一到Class类中的类型但是又和原始类型其名的类型。
 * <p>
 * ParameterizedType：表示一种参数化类型，比如Collection<String>
 * GenericArrayType:表示一种元素类型是参数化类型或者类型变量的数组类型
 * TypeVariable：是各种类型变量的公共父接口
 * WildcardType:代表一种通配符类型表达式
 *
 * @author : lzw
 * @date : 2022/5/5
 * @since : 1.0
 */
public class Test11 {


    public void test01(Map<String, User> map, List<User> list) {
        System.out.println("test01");
    }

    public Map<String, User> test02() {
        return null;
    }

    public static void main(String[] args) throws NoSuchMethodException {
        Method method = Test11.class.getMethod("test01", Map.class, List.class);

        // 获得泛型的参数类型
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        for (Type genericParameterType : genericParameterTypes) {
            System.out.println("#" + genericParameterType);
            // 判断泛型的参数类型是否是参数化类型，所谓参数化类型指的是 Collection<String>这种类型的参数
            if (genericParameterType instanceof ParameterizedType) {
                // 获取真实参数信息
                Type[] actualTypeArguments = ((ParameterizedType) genericParameterType).getActualTypeArguments();
                for (Type actualTypeArgument : actualTypeArguments) {
                    System.out.println(actualTypeArgument);
                }
            }
        }
        System.out.println("=====================");
        method = Test11.class.getMethod("test02", null);
        // 获取返回值泛型类型
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType instanceof ParameterizedType) {
            // 获取真实参数信息
            Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
            for (Type actualTypeArgument : actualTypeArguments) {
                System.out.println(actualTypeArgument);
            }
        }

    }
}
