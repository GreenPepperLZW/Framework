package com.lzw.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 参数化测试
 *
 * @author : lzw
 * @date : 2022/12/14
 * @since : 1.0
 */
@DisplayName("参数化测试")
public class ParameterTest {


    static Stream<String> stringProvider() {
        return Stream.of("apple", "banana");
    }

    @ParameterizedTest
    @ValueSource(strings = {"racecar", "radar", "able was I ere I saw elba"})
    void test01(String candidate) {
        // 这五个挨个传入
        System.out.println(candidate);
    }

    @ParameterizedTest
    @MethodSource("stringProvider")
        // 参数来自一个方法
    void testWithExplicitLocalMethodSource(String argument) {
        System.out.println(argument);
        assertNotNull(argument);
    }
}
