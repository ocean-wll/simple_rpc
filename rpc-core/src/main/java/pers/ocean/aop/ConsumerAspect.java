package pers.ocean.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 服务调用方切面
 *
 * @author : ocean_wll
 * @since 2021/7/3
 */
@Aspect
@Component
public class ConsumerAspect {

    @Pointcut("@annotation(pers.ocean.annotation.Consumer)")
    public void point() {
    }

    @Around("point()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        System.out.println("before");
        try {
            // TODO ocean_wll 获取 @Consumer 中配置的信息去注册中心获取对应的服务地址，并生成代理类返回
            proceedingJoinPoint.proceed();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.out.println("after");
        return null;
    }
}
