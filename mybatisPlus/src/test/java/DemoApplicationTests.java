import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lzw.PlusTestApplication;
import lzw.entity.User;
import lzw.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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


}
