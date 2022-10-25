package com.lzw.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * 切面类
 *
 * @author : lzw
 * @date : 2022/10/10
 * @since : 1.0
 */
@Aspect//这个一个切面类
public class LogAspects {

    /**
     * 抽取一个公共切入点
     * 1.本类引用，例如：@Before("pointCut()")
     * 2.外部切面类引用：使用方法全名引用：com.lzw.aop.LogAspects.pointCut()
     */
    @Pointcut("execution(public * com.lzw.aop.MathCalculator.*(..))")
    public void pointCut() {
    }


    /**
     * 只增强div一个方法，切点的写法：public int com.lzw.aop.MathCalculator.div(int, int)
     * 增强MathCalculator下所有方法，并且不区分参数和返回类型：public * com.lzw.aop.MathCalculator.*(..)
     */
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println(joinPoint.getSignature().getName() + "除法运行....参数列表是{" + Arrays.asList(args) + "}");
    }

    @After("pointCut()")
    public void logEnd() {
        System.out.println("除法结束....");
    }

    // JoinPoint参数必须放在第一位
    @AfterReturning(value = "pointCut()", returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result) {
        System.out.println("除法正常返回....运行结果是{" + result + "}");
    }

    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void logException(Exception exception) {
        System.out.println("除法异常....异常信息是{" + exception.getMessage() + "}");
    }

}
