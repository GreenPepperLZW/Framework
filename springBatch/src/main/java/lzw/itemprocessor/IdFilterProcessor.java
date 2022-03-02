package lzw.itemprocessor;

import lzw.pojo.Customer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @author : lzw
 * @date : 2022/3/2
 * @since : 1.0
 */
@Component
public class IdFilterProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public Customer process(Customer customer) throws Exception {
        if (customer.getId() % 2 == 0)
            return customer;
        else
            return null;
    }
}
