package lzw.reflection;

/**
 * 测试类加载器
 * @author : lzw
 * @date : 2022/4/25
 * @since : 1.0
 */
public class Test07 {

    public static void main(String[] args) throws ClassNotFoundException {
        /**
         * BootStrap Class 引导类加载器，用C++编写，是JVM自带的类加载器，负责Java平台核心库，用来装载核心类库，jre/lib/rt.jar，java.lang.io,java.lang.Math都在其中。该加载器类无法直接获取
         *
         * Extcnsion 扩展类加载器：负责 jre/lib/ext 目录下的jar包或 -D java.ext.dirs 指定目录下的jar包装入工作库
         *
         * System 系统类加载器：负责java -classpath 或 -D Java.class.path所指的目录下的类与jar包装入工作，是最常用的加载器。
         */

        // 获取类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);// sun.misc.Launcher$AppClassLoader@18b4aac2

        // 获取系统类加载器的父加载器，拓展类加载器
        ClassLoader parent = systemClassLoader.getParent();
        System.out.println(parent); // sun.misc.Launcher$ExtClassLoader@4d7e1886

        //获取拓展类加载器的父加载器，根加载器
        ClassLoader parent1 = parent.getParent();
        System.out.println(parent1); // null

        // 测试当前类是哪个加载器加载的
        ClassLoader classLoader = Class.forName("lzw.reflection.Test07").getClassLoader();
        System.out.println(classLoader); // sun.misc.Launcher$AppClassLoader@18b4aac2

        // 测试jdk内置的类是由谁加载的
        ClassLoader classLoader1 = Class.forName("java.lang.Math").getClassLoader();
        System.out.println(classLoader1); // null

        // 获取系统类加载器可以加载的路径
        System.out.println(System.getProperty("java.class.path").replaceAll(";", "\n"));

        /*
        C:\Program Files\Java\jdk1.8.0_301\jre\lib\charsets.jar
        C:\Program Files\Java\jdk1.8.0_301\jre\lib\deploy.jar
        C:\Program Files\Java\jdk1.8.0_301\jre\lib\ext\access-bridge-64.jar
        C:\Program Files\Java\jdk1.8.0_301\jre\lib\ext\cldrdata.jar
        C:\Program Files\Java\jdk1.8.0_301\jre\lib\ext\dnsns.jar
        C:\Program Files\Java\jdk1.8.0_301\jre\lib\ext\jaccess.jar
        C:\Program Files\Java\jdk1.8.0_301\jre\lib\ext\jfxrt.jar
        C:\Program Files\Java\jdk1.8.0_301\jre\lib\ext\localedata.jar
        C:\Program Files\Java\jdk1.8.0_301\jre\lib\ext\nashorn.jar
        C:\Program Files\Java\jdk1.8.0_301\jre\lib\ext\sunec.jar
        C:\Program Files\Java\jdk1.8.0_301\jre\lib\ext\sunjce_provider.jar
        C:\Program Files\Java\jdk1.8.0_301\jre\lib\ext\sunmscapi.jar
        C:\Program Files\Java\jdk1.8.0_301\jre\lib\ext\sunpkcs11.jar
        C:\Program Files\Java\jdk1.8.0_301\jre\lib\ext\zipfs.jar
        C:\Program Files\Java\jdk1.8.0_301\jre\lib\javaws.jar
        C:\Program Files\Java\jdk1.8.0_301\jre\lib\jce.jar
        C:\Program Files\Java\jdk1.8.0_301\jre\lib\jfr.jar
        C:\Program Files\Java\jdk1.8.0_301\jre\lib\jfxswt.jar
        C:\Program Files\Java\jdk1.8.0_301\jre\lib\jsse.jar
        C:\Program Files\Java\jdk1.8.0_301\jre\lib\management-agent.jar
        C:\Program Files\Java\jdk1.8.0_301\jre\lib\plugin.jar
        C:\Program Files\Java\jdk1.8.0_301\jre\lib\resources.jar

        C:\Program Files\Java\jdk1.8.0_301\jre\lib\rt.jar

        D:\Code\framework\annotation\target\classes

        D:\Software\maven\mavenRepo\org\springframework\boot\spring-boot-starter\2.6.1\spring-boot-starter-2.6.1.jar
        D:\Software\maven\mavenRepo\org\springframework\boot\spring-boot\2.6.1\spring-boot-2.6.1.jar
        D:\Software\maven\mavenRepo\org\springframework\spring-context\5.3.13\spring-context-5.3.13.jar
        D:\Software\maven\mavenRepo\org\springframework\spring-aop\5.3.13\spring-aop-5.3.13.jar
        D:\Software\maven\mavenRepo\org\springframework\spring-beans\5.3.13\spring-beans-5.3.13.jar
        D:\Software\maven\mavenRepo\org\springframework\spring-expression\5.3.13\spring-expression-5.3.13.jar
        D:\Software\maven\mavenRepo\org\springframework\boot\spring-boot-autoconfigure\2.6.1\spring-boot-autoconfigure-2.6.1.jar
        D:\Software\maven\mavenRepo\org\springframework\boot\spring-boot-starter-logging\2.6.1\spring-boot-starter-logging-2.6.1.jar
        D:\Software\maven\mavenRepo\ch\qos\logback\logback-classic\1.2.7\logback-classic-1.2.7.jar
        D:\Software\maven\mavenRepo\ch\qos\logback\logback-core\1.2.7\logback-core-1.2.7.jar
        D:\Software\maven\mavenRepo\org\slf4j\slf4j-api\1.7.32\slf4j-api-1.7.32.jar
        D:\Software\maven\mavenRepo\org\apache\logging\log4j\log4j-to-slf4j\2.14.1\log4j-to-slf4j-2.14.1.jar
        D:\Software\maven\mavenRepo\org\apache\logging\log4j\log4j-api\2.14.1\log4j-api-2.14.1.jar
        D:\Software\maven\mavenRepo\org\slf4j\jul-to-slf4j\1.7.32\jul-to-slf4j-1.7.32.jar
        D:\Software\maven\mavenRepo\jakarta\annotation\jakarta.annotation-api\1.3.5\jakarta.annotation-api-1.3.5.jar
        D:\Software\maven\mavenRepo\org\springframework\spring-core\5.3.13\spring-core-5.3.13.jar
        D:\Software\maven\mavenRepo\org\springframework\spring-jcl\5.3.13\spring-jcl-5.3.13.jar
        D:\Software\maven\mavenRepo\org\yaml\snakeyaml\1.29\snakeyaml-1.29.jar

        C:\Program Files\JetBrains\IntelliJ IDEA 2022.1\lib\idea_rt.jar
         */
    }
}
