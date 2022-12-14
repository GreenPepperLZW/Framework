package com.lzw.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.util.concurrent.TimeUnit;

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
     * 重复测试
     */
    @Test
    @RepeatedTest(5)
    void test04() {
        System.out.println(4);
    }

    /**
     * 在每一个测试方法之后执行
     */
    @AfterEach
    void testAfterEach() {
        System.out.println("测试结束");
    }


}
