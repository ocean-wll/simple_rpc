package pers.ocean.rpc.demo.provider.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.ocean.api.RpcResolver;
import pers.ocean.rpc.demo.api.service.OrderService;
import pers.ocean.rpc.demo.api.service.UserService;
import pers.ocean.rpc.demo.provider.resolver.SpringResolver;
import pers.ocean.rpc.demo.provider.service.OrderServiceImpl;
import pers.ocean.rpc.demo.provider.service.UserServiceImpl;
import pers.ocean.server.RpcInvoker;

/**
 * 将bean交由spring容器管理
 *
 * @author ocean_wll
 * @date 2021/6/30
 */
@Configuration
public class BeanConfiguration {

    @Bean("pers.ocean.rpc.demo.api.service.OrderService")
    public OrderService orderService() {
        return new OrderServiceImpl();
    }

    @Bean("pers.ocean.rpc.demo.api.service.UserService")
    public UserService userService() {
        return new UserServiceImpl();
    }

    @Bean
    public RpcResolver springResolver() {
        return new SpringResolver();
    }

    @Bean
    public RpcInvoker rpcInvoker(@Autowired RpcResolver rpcResolver) {
        return new RpcInvoker(rpcResolver);
    }
}
