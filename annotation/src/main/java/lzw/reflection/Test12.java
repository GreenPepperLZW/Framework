package lzw.reflection;

import java.lang.annotation.*;

/**
 * 反射操作注解
 *
 * @author : lzw
 * @date : 2022/5/5
 * @since : 1.0
 */
public class Test12 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException {
        Class c1 = Class.forName("lzw.reflection.Student2");

        // 通过反射获取值
        Annotation[] annotations = c1.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }

        // 获得注解的value值
        Table table = (Table)c1.getAnnotation(Table.class);
        System.out.println("注解的值"+table.value());

        // 获得类指定的注解
        java.lang.reflect.Field file = c1.getDeclaredField("age");
        Field annotation = file.getAnnotation(Field.class);
        System.out.println(annotation.columNmae());
        System.out.println(annotation.type());
        System.out.println(annotation.length());
    }
}

@Table("db_student")
class Student2{
    @Field(columNmae = "id",type = "int",length = 10)
    private int id;

    @Field(columNmae = "age",type = "int",length = 3)
    private int age;

    @Field(columNmae = "name",type = "String",length = 3)
    private String name;

    public Student2(int id, int age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    public Student2() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

/**
 * 类名的注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Table{
    String value();
}

/**
 * 参数的注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Field{
    String columNmae();
    String type();
    int length();
}
