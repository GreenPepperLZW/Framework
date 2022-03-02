package lzw.itemreader;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * ItemReader基本使用
 *
 * @author : lzw
 * @date : 2022/2/28
 * @since : 1.0
 */
//@Configuration
public class ItemReaderDemo {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job itemReaderDemoJob() {
        return jobBuilderFactory.get("itemReaderDemoJob")
                .start(itemReaderDemoStep())
                .build();
    }


    @Bean
    public Step itemReaderDemoStep() {
        return stepBuilderFactory.get("itemReaderDemoStep")
                .<String,String>chunk(2)
                .reader(itemReaderDemoRead())
                .writer(list->{
                    for (Object item:list){
                        System.out.println(item+"...");
                    }
                })
                .build();
    }

    @Bean
    public ItemReader<String> itemReaderDemoRead() {
        List<String> data = Arrays.asList("鼠","牛","虎","兔");
        return new MyReader(data);
    }
}
