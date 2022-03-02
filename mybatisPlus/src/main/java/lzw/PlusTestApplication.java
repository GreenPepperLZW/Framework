package lzw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : lzw
 * @date : 2022/3/1
 * @since : 1.0
 */
@SpringBootApplication
@MapperScan("lzw.mapper.**")
public class PlusTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlusTestApplication.class, args);
    }
}
