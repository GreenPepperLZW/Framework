package lzw.joblauncher;

import lzw.pojo.Customer;
import org.springframework.batch.item.ItemProcessor;

/**
 * @author : lzw
 * @date : 2022/3/2
 * @since : 1.0
 */

public class FirstNameUpperJobLauncherProcessor implements ItemProcessor<Customer, Customer> {
    /*ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };*/
    private Long order;

    public FirstNameUpperJobLauncherProcessor(Long order) {
        this.order = order;
    }

    /*@AfterProcess
    public void afterProcess(Object o, Object o1) throws Exception {
        System.out.println("afterProcess");
    }*/


    public FirstNameUpperJobLauncherProcessor() {
    }

    @Override
    public Customer process(Customer customer) throws Exception {
        order++;
//        Integer integer = threadLocal.get();
        Customer customer1 = new Customer();
        String name = Thread.class.getName();
        long id = Thread.currentThread().getId();
        System.out.println("线程名称：" + name + ",线程id:" + id);
        customer1.setId(customer.getId());
        customer1.setLastName(customer.getLastName());
        customer1.setFirstName(customer.getFirstName().toUpperCase());
        customer1.setBirthday(customer.getBirthday());
//        Integer integer1 = ++integer;
//        threadLocal.set(integer1);
        return customer1;
    }
}
