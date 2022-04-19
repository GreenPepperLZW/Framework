package lzw.reflection;


/**
 * 测试Class 类的创建方式有哪些
 * @author : lzw
 * @date : 2022/4/13
 * @since : 1.0
 */
public class Test02 {

    public static void main(String[] args) throws ClassNotFoundException {
        Person person = new Student();
        System.out.println("这个人是："+person.name);

        // 方式1：通过对象获得 Class 类
        Class c1 = person.getClass();
        System.out.println(c1.hashCode());


        // 方式2：通过 包名获取
        Class c2 = Class.forName("lzw.reflection.Student");
        System.out.println(c2.hashCode());

        // 方式3：通过类名.class 获得
        Class<Student> c3 = Student.class;
        System.out.println(c3.hashCode());

        // 方式4：基本内置类型的包装类型都有一个Type属性
        Class<Integer> c4 = Integer.TYPE;
        System.out.println(c4);

        // 获得父类类型
        Class c5 = c1.getSuperclass();
        System.out.println(c5);


    }
}


class Person{

    public String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}


class Student extends Person{

    public Student(){
        this.name = "学生";
    }

}


class Teacher extends Person{

    public Teacher(){
        this.name = "老师";
    }

}
