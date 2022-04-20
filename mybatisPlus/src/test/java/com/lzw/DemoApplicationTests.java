package com.lzw;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lzw.PlusTestApplication;
import lzw.entity.User;
import lzw.mapper.UserMapper;
import lzw.restMap.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author : lzw
 * @date : 2022/3/1
 * @since : 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlusTestApplication.class)
public class DemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
        // 查询所有数据
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
        // 数据库中的数据默认初始5条，则判断查询出来的集合数量是否等于5
        System.out.println("数据条数"+ users.size());
    }

    /**
     * 条件构造器中使用sum函数
     */
    @Test
    public void test2() {
        QueryWrapper<User> userWrapper = new QueryWrapper<User>();
        userWrapper.select("sum(age) as total ");
        List<Object> objects = userMapper.selectObjs(userWrapper);

        System.out.println("总数为"+objects.get(0));

    }

    /**
     * 1对1联表分页查询
     */
    @Test
    public void testPage() {
        Page<User> page = new Page<>(1, 3);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("name","大boss");
        IPage<UserDto> userDtoIPage = userMapper.pageCanditionQuery(page, queryWrapper);

        List<UserDto> records = userDtoIPage.getRecords();
        records.forEach(System.out::println);
    }

    /**
     * 测试条件查询
     */
    @Test
    public void testCondition() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("manager_id", "1088248166370832384");
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user.toString());
    }

    /**
     * 查询年龄总和与数据条数，使用Object接口
     */
    @Test
    public void testReturnObject() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("name")
                .select("sum(age) as age,count(*) as count");
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        Map<String, Object> map = maps.get(0);

        System.out.println("年龄总和"+map.get("age"));
        System.out.println("数据总条数"+map.get("count"));
        System.out.println(maps);
    }

    @Test
    public void testOptionIn() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.in("name",new ArrayList<String>(){{add("大boss");add("王天风");}})
                .and(new Consumer<QueryWrapper<User>>() {
                    @Override
                    public void accept(QueryWrapper<User> userQueryWrapper) {
                        userQueryWrapper.in("age",new ArrayList<String>(){{add("40");add("25");}});
                    }
                });

        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);

        // SELECT id,name,age,email,manager_id FROM user WHERE (name IN ("大boss","王天风") AND (age IN (40,25)));

        // SELECT * from user u WHERE (u.age,u.`name`) in ((40,"大boss"),(25,"王天风"));
    }

    @Test
    public void testOptionIn2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);
    }


}
