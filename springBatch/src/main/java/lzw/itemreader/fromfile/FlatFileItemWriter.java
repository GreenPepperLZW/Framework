package lzw.itemreader.fromfile;

import lzw.pojo.Customer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : lzw
 * @date : 2022/2/28
 * @since : 1.0
 */
@Component("flatFileItemWriter")
public class FlatFileItemWriter implements ItemWriter<Customer> {
    @Override
    public void write(List<? extends Customer> list) throws Exception {
        for (Customer customer:list){
            System.out.println(customer);
        }
    }
}
