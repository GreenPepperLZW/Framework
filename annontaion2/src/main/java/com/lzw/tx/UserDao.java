package com.lzw.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Random;
import java.util.UUID;

/**
 * @author : lzw
 * @date : 2022/10/13
 * @since : 1.0
 */
@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert() {
        //language=MySQL这行注释可以在写sql语句时有提示
        //language=MySQL
        String sql = "INSERT INTO `test`.`user`(`id`, `name`, `age`, `email`, `manager_id`, `create_time`) VALUES (?, ?, ?, 'yg@163.com', 11, '2022-10-14 09:15:15')";
        Random random = new Random();
        int id = Math.abs(random.nextInt());
        String name = UUID.randomUUID().toString().substring(0, 5);

        jdbcTemplate.update(sql, id, name, 18);
    }
}
