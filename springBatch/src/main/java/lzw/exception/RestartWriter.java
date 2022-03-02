package lzw.exception;

import lzw.pojo.Customer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : lzw
 * @date : 2022/3/1
 * @since : 1.0
 */
@Component("restartWriter")
public class RestartWriter implements ItemWriter<Customer> {

    @Override
    public void write(List<? extends Customer> list) throws Exception {
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }
}
