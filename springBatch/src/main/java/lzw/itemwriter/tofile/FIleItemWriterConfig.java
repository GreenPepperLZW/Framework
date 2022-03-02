package lzw.itemwriter.tofile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lzw.pojo.Customer;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

/**
 * 输出数据到文件中
 *
 * @author : lzw
 * @date : 2022/3/2
 * @since : 1.0
 */
@Component
public class FIleItemWriterConfig {

    @Bean
    public FlatFileItemWriter<Customer> fileItemWriter() {
        // 把Customer对象转成字符串输出到文件
        FlatFileItemWriter<Customer> writer = new FlatFileItemWriter<Customer>();
        String path = "D:\\temp/customer.txt";
        writer.setResource(new FileSystemResource(path));
        // 把Customer对象转成字符串
        writer.setLineAggregator(new LineAggregator<Customer>() {
            ObjectMapper mapper = new ObjectMapper();

            @Override
            public String aggregate(Customer customer) {
                String str = null;
                try {
                    // 转为json字符串
                    str = mapper.writeValueAsString(customer);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return str;
            }
        });
        try {
            writer.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return writer;

    }

}
