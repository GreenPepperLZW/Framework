package com.lzw.config;

/*
 * AOP【动态代理】：
 *      指在程序运行期间动态的将某段代码切入到指定的位置进行运行的编程方式
 *  1.导入AOP模块，Spring AOP
 *  2.定义一个业务逻辑类（MathCalculator），在业务逻辑运行的时候将日志进行打印（方法之前、方法运行结束，方法出现异常等情况）
 *  3.定义一个日志切面类（LogAspects），切面类里面的方法需要动态感知MathCalculator.div运行到那一步
 *          通知方法：
 *              前置通知：(@Before)，在目标方法运行之前运行，即LogAspects中的logStart
 *              后置通知：(@After)，在目标方法运行结束之后运行，即LogAspects中的logEnd,无论方法时正常结束还是异常结束都会执行
 *              返回通知：(@AfterReturning),在目标方法正常返回以后运行，即LogAspects中的logReturn
 *              异常通知：(@AfterThrowing)，logException,在目标方法出现异常以后运行，即LogAspects中的logException
 *              环绕通知：(@Around)，动态代理，手动推进目标方法运行，(joinPoint.proceed())
 * 4.给切面类的目标方法标注何时何地运行（通知注解）
 * 5.将切面类和业务逻辑类（目标方法所在类）都加入到容器中
 * 6.必须告诉Spring哪个类时切面类(给切面类上加一个注解，@Aspect)
 * 7.给配置加入@EnableAspectJAutoProxy，相当于xml配置中的【<aop:aspectj-autoproxy></aop:aspectj-autoproxy>】，开启基于注解的aop模式
 *
 * 三步：
 *  1)、将业务逻辑组件和切面类都加入到容器中，告诉Spring哪个是切面类(@Aspect)
 *  2)、在切面类上的每一个通知方法上都标注注解，告诉Spring何时何地运行（切入点表达式）
 *  3)、开始基于注解的aop模式，@EnableAspectJAutoProxy
 *
 */

import com.lzw.aop.LogAspects;
import com.lzw.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author : lzw
 * @date : 2022/10/9
 * @since : 1.0
 */
@Configuration
@EnableAspectJAutoProxy
public class MainConfigOfAOP {

    // 将业务逻辑类加入到容器中
    @Bean
    public MathCalculator mathCalculator() {
        return new MathCalculator();
    }

    // 将切面类加入到容器中
    @Bean
    public LogAspects logAspects() {
        return new LogAspects();
    }

}
