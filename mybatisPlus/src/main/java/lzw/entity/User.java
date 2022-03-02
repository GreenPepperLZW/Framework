package lzw.entity;

import lombok.Data;

/**
 * @author : lzw
 * @date : 2022/3/1
 * @since : 1.0
 */


@Data
public class User {
    private Long id;

    private String name;

    private Integer age;

    private String email;

    private Long managerId;


}
