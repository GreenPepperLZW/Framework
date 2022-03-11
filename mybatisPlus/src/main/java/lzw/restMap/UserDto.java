package lzw.restMap;

import lombok.Data;

/**
 * 连表查询后返回的结果
 * @author : lzw
 * @date : 2022/3/11
 * @since : 1.0
 */
@Data
public class UserDto {

    private Long id;

    private String name;

    private Integer age;

    private String email;

    private Long managerId;

    private String address;

    private String phone;
}
