package lzw.conf;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

/**
 * 自定义决策器中的规则
 *
 * @author : lzw
 * @date : 2022/2/25
 * @since : 1.0
 */
public class MyDecider implements JobExecutionDecider {

    private int count;

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        count++;
        if(count%2==0){
            return new FlowExecutionStatus("even偶数");
        }else {
            return new FlowExecutionStatus("odd奇数");
        }
    }
}
