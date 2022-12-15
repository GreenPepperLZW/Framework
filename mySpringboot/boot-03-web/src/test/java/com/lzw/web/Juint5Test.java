package com.lzw.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : lzw
 * @date : 2022/12/14
 * @since : 1.0
 */
@Slf4j
@DisplayName("Juint5测试")
public class Juint5Test {


    /**
     * 在所有测试方法之前执行，只执行一次，在@BeforeEach 之前执行
     * 必须为静态方法
     */
    @BeforeAll
    static void beforeAll() {
        System.out.println("在所有测试方法之前执行");
    }

    /**
     * 在所有测试方法之前执行，只执行一次，在@AfterEach 之后执行
     * 必须为静态方法
     */
    @AfterAll
    static void afterAll() {
        System.out.println("在所有测试方法之后执行");
    }

    /**
     * @DisplayName 注解，测试类或测试方法设置展示名称
     */
    @Test
    @DisplayName("DisplayName测试")
    public void test01() {
        System.out.println(1);
    }

    @Test
    @Disabled // 禁用
    @DisplayName("test02方法")
    public void test02() {
        System.out.println(2);
    }

    /**
     * 在每一个测试方法开始之前执行
     */
    @BeforeEach
    void testBeforeEach() {
        System.out.println("测试就要开始了");
    }

    /**
     * 方法执行超时报错
     */
    @Test
    @DisplayName("超时测试")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void test03() throws InterruptedException {
        Thread.sleep(600);
    }

    /**
     * 重复测试，测试方法自动重复执行多少次
     */
    @Test
    @RepeatedTest(5)
    void test04() {
        System.out.println(4);
    }


    /**
     * 简单断言测试
     */
    @Test
    @DisplayName("简单断言测试")
    void testSipleAssertions() {
        int cal = cal(1, 4);
        // 期望值，实际返回的值
        assertEquals(5, cal, "业务逻辑失败");

        //判断是不是同一个对象
        Object obj1 = new Object();
        Object obj2 = new Object();
        assertSame(obj2, obj1, "不是同一个对象");
    }

    /**
     * 判断两个对象或原始类型的数组是否相等
     */
    @Test
    void testArray() {
        assertArrayEquals(new int[]{1, 2}, new int[]{1, 2});
    }

    /**
     * 组合断言，assertAll方法接收多个org.junit.jupiter.api.Executable函数式接口的实例作为要验证的断言，可以通过lambda表达式提供给这些断言
     */
    @Test
    void testAll() {

        assertAll("Math",
                () -> assertEquals(2, 1 + 1),
                () -> assertTrue(1 > 0)
        );
    }

    @Test
    @DisplayName("断言异常")
    void exceptionTest() {
        // 如果没有指定的异常，说明指定的方法是不对的，断定业务逻辑一定会出现异常
        assertThrows(ArithmeticException.class,
                () -> {
                    int i = 10 / 0;
                },
                "业务逻辑居然没抛错，不可能呀");
    }

    @Test
    @DisplayName("方法超时异常断言")
    void testAssertTimout() {
        assertTimeout(Duration.ofMillis(1000), () -> Thread.sleep(2000));
    }

    /**
     * 使得方法快速失败
     */
    @Test
    @DisplayName("快速失败")
    void testShouldFail() {
        if (true) {
            fail("测试失败");
        }
    }

    int cal(int i, int j) {
        return i + j;
    }

    /**
     * 测试前置条件，如果前置条件不满足，则不继续往下执行，不会抛错
     */
    @Test
    @DisplayName("前置条件")
    void testAssumptions() {
        Assumptions.assumeTrue(1 > 0, "条件不满足");
        System.out.println("前置条件测试");
    }

    /**
     * 在每一个测试方法之后执行
     */
    @AfterEach
    void testAfterEach() {
        System.out.println("测试结束");
    }


}
