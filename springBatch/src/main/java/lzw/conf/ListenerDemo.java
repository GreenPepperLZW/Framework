package lzw.conf;

import lzw.listener.MyChunkListener;
import lzw.listener.MyJobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * 演示监听器的使用
 *
 * @author : lzw
 * @date : 2022/2/25
 * @since : 1.0
 */
//@Configuration
public class ListenerDemo {

    /**
     * @Description: 注入创建任务对象的对象
     */
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    /**
     * @Description: 任务的执行由Step决定,注入创建Step对象的对象
     */
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job listenerJob() {
        return jobBuilderFactory.get("listenerJob")
                .start(step1())
                // 使用自己实现的监听器类
                .listener(new MyJobListener())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").
                // 规定读取和输出的数据类型每读完2个数据进行一次输出处理
                <String,String>chunk(2)
                //容错
                .faultTolerant()
                .listener(new MyChunkListener())
                //数据的读取
                .reader(read())
                //数据的写入/输出
                .writer(write())
                .build();
    }


    /**
     * 写数据
     */
    @Bean
    public ItemWriter<String> write() {
        return new ItemWriter<String>() {
            @Override
            public void write(List<? extends String> list) throws Exception {
                for(String item:list){
                    System.out.println(item);
                }
            }
        };
    }


    /**
     * @Description: 读取数据
     */
    @Bean
    public ItemReader<String> read() {
        return new ListItemReader<>(Arrays.asList("java","spring","mybatis"));
    }

}
