package lzw.itemwriter;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 读取数据进行输出
 *
 * @author : lzw
 * @date : 2022/3/2
 * @since : 1.0
 */
@Component("myWriter")
public class MyWriter implements ItemWriter<String> {

    @Override
    public void write(List<? extends String> list) throws Exception {
        //输出一批的数量，chunk的值
        System.out.println(list.size());
        for (String str : list) {
            System.out.println(str);
        }
    }
}
