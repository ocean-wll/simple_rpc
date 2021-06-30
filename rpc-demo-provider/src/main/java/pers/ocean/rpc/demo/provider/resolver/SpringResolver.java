package pers.ocean.rpc.demo.provider.resolver;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import pers.ocean.api.RpcResolver;

/**
 * 从spring 容器中获取对应的bean
 *
 * @author ocean_wll
 * @date 2021/6/30
 */
public class SpringResolver implements RpcResolver, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object resolver(String serviceName) {
        return this.applicationContext.getBean(serviceName);
    }
}
