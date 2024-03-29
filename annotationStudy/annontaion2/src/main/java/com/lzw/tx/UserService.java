package com.lzw.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : lzw
 * @date : 2022/10/13
 * @since : 1.0
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;


    @Transactional(transactionManager = "transactionManager2")
    public void insertUser() {
        userDao.insert();
        System.out.println("插入完成");
        // 抛出异常，测试是否回滚
        int i = 10 / 0;
    }
}
