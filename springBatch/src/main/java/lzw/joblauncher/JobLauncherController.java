package lzw.joblauncher;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 调用批处理任务
 *
 * @author : lzw
 * @date : 2022/3/2
 * @since : 1.0
 */
@RestController
public class JobLauncherController {


    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    public Job jobLauncherDemoJob;

    @RequestMapping("/job/{msg}/{fileName}")
    public String jobRun1(@PathVariable String msg,@PathVariable String fileName) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        // 把接收到的参数值传给任务
        ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
            @Override
            protected Integer initialValue() {
                return 1;
            }
        };
        JobParameters parameters = new JobParametersBuilder()
                .addString("msg",msg)
                .addString("fileName",fileName)
                // 加入时间戳参数，使一个任务可以执行多次，JobInstance是由job的名称和执行该job的参数组成的，当执行job时，会由于参数相同
                // 会认为是同一个job实例，如果该job已经执行过，就会抛出异常，判断job是否执行过的状态是保存到job Repository中的
                .addDate("date",new Date())
                .addLong("order",1L)
                .toJobParameters();

        // 启动任务，并把参数传给任务
        jobLauncher.run(jobLauncherDemoJob,parameters);


        return "job success";
    }
}
