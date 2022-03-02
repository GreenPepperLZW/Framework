package lzw.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.annotation.BeforeJob;

/**
 * 使用实现接口的方式实现Job执行前后的监听
 *
 * @author : lzw
 * @date : 2022/2/25
 * @since : 1.0
 */
public class MyJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("job的名称："+jobExecution.getJobInstance().getJobName()+"---before");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("job的名称："+jobExecution.getJobInstance().getJobName()+"---after");
    }
}
