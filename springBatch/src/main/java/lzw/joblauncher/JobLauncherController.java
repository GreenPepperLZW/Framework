package lzw.joblauncher;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 调用批处理任务
 *
 * @author : lzw
 * @date : 2022/3/2
 * @since : 1.0
 */
@RestController
public class JobLauncherController {

    @RequestMapping("/job/{msg}")
    public String jobRun1(@PathVariable String msg){

        return null;
    }
}
