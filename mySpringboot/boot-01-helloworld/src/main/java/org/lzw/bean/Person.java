package org.lzw.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 练习yml中各种数据类型写法
 *
 * @author : lzw
 * @date : 2022/11/16
 * @since : 1.0
 */

@Data
@ToString
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "person")
public class Person {

    private String userName;
    private boolean boss;
    private Date birth;
    private Integer age;
    private Pet pet;

    private String[] interests;
    private List<String> animal;

    private Map<String, Object> score;
    private Set<Double> salary;
    private Map<String, List<Pet>> allPets;
}
