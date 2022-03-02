package lzw.itemreader.frommulti;

import lzw.pojo.Customer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 从多个文件中读取信息
 *
 * @author : lzw
 * @date : 2022/3/1
 * @since : 1.0
 */
@Component("multiFileWriter")
public class MultiFileWriter implements ItemWriter<Customer> {

    @Override
    public void write(List<? extends Customer> list) throws Exception {
        for (Customer customer:list){
            System.out.println(customer);
        }
    }
}
