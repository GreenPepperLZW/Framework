package lzw.itemreader.fromdb;

import lzw.pojo.User;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : lzw
 * @date : 2022/2/28
 * @since : 1.0
 */
@Component("dbJdbcWriter")
public class DbJdbcWriter implements ItemWriter<User> {


    @Override
    public void write(List<? extends User> list) throws Exception {
        for (User user : list) {
            System.out.println(user);
        }
    }
}
