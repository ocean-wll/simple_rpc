package pers.ocean.rpc.demo.consumer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import pers.ocean.api.Filter;
import pers.ocean.api.LoadBalancer;
import pers.ocean.api.Router;
import pers.ocean.api.RpcRequest;
import pers.ocean.client.RpcClient;
import pers.ocean.rpc.demo.api.model.Order;
import pers.ocean.rpc.demo.api.model.User;
import pers.ocean.rpc.demo.api.service.OrderService;
import pers.ocean.rpc.demo.api.service.UserService;

import java.util.List;

/**
 * 消费者
 *
 * @author ocean_wll
 * @date 2021/6/30
 */
@SpringBootApplication
public class RpcConsumerApplication {

    public static void main(String[] args) {

        // TODO ocean_wll 后续可以改成直接根据name去获取service
        UserService userService = RpcClient.create(UserService.class, "http://127.0.0.1:8080/", new OceanFilter());
        User user = userService.findById(1);
        System.out.println("find user id = 1 from server: " + user.getName());

        OrderService orderService = RpcClient.create(OrderService.class, "http://127.0.0.1:8080/", new OceanFilter());
        Order order = orderService.findById(1);
        System.out.println("find order id = 1 from server: " + order.getName());

//        UserService userService1 = RpcClient.createFromRegistry(UserService.class, "127.0.0.1:2181", new TagRouter(),
//                new FirstLoadBalancer(), new OceanFilter());
    }

    /**
     * 自定义路由器
     */
    private static class TagRouter implements Router {

        @Override
        public List<String> route(List<String> urls) {
            return urls;
        }
    }

    /**
     * 自定义负载均衡器
     */
    private static class FirstLoadBalancer implements LoadBalancer {

        @Override
        public String select(List<String> urls) {
            return urls.get(0);
        }
    }

    /**
     * 自定义过滤器
     */
    private static class OceanFilter implements Filter {

        @Override
        public Boolean filter(RpcRequest request) {
            return true;
        }
    }
}
