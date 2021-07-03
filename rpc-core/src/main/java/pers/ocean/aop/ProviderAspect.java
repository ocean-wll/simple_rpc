package pers.ocean.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 服务提供方切面
 *
 * @author : ocean_wll
 * @since 2021/7/3
 */
@Aspect
@Component
public class ProviderAspect {

    @Pointcut("@annotation(pers.ocean.annotation.Provider)")
    public void point() {
    }

    @Around("point()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        System.out.println("before");
        try {
            // TODO ocean_wll 获取 @Provider 注解中配置的信息，然后往注册中心注册数据
            proceedingJoinPoint.proceed();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.out.println("after");
        return null;
    }
}
