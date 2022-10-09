package lzw.reflection;

import java.lang.reflect.Field;

/**
 * @author : lzw
 * @date : 2022/7/20
 * @since : 1.0
 */
public class Test13 {
    public static void main(String[] args) throws IllegalAccessException {
        Person3 person = new Person3(1, "", null);

        Class aClass = person.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();

        for (Field filed : declaredFields) {
            filed.setAccessible(true);
            String name = filed.getName();
            Object o = filed.get(person);
            if (o == null || o == "") {
                System.out.println("字段的名称" + name);
            }
            System.out.println("====" + o);
        }
    }
}


class Person3 {

    private int age;


    private String name;

    private String address;

    public Person3(int age, String name, String address) {
        this.age = age;
        this.name = name;
        this.address = address;
    }

    public Person3() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
