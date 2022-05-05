package lzw.reflection;

/**
 * @author : lzw
 * @date : 2022/4/13
 * @since : 1.0
 */
public class Test01 {

    public static void main(String[] args) throws ClassNotFoundException {
        Class c1 = Class.forName("lzw.reflection.User");
        System.out.println(c1);

        Class c2 = Class.forName("lzw.reflection.User");
        Class c3 = Class.forName("lzw.reflection.User");
        Class c4 = Class.forName("lzw.reflection.User");

        // 一个类在内存中只有一个class
        // 一个类被加载后，类的整个结构都会被封装在Class对象中
        System.out.println(c2.hashCode());
        System.out.println(c3.hashCode());
        System.out.println(c4.hashCode());


    }

}

class User {

    private String name;
    private int age;
    private int id;

    public User(String name, int age, int id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        System.out.println("设置名称：" + name);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                '}';
    }
}
