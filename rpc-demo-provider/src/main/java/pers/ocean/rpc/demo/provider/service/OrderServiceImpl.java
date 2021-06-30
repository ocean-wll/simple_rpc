package pers.ocean.rpc.demo.provider.service;

import pers.ocean.rpc.demo.api.model.Order;
import pers.ocean.rpc.demo.api.service.OrderService;

/**
 * 订单接口实现类
 *
 * @author ocean_wll
 * @date 2021/6/30
 */
public class OrderServiceImpl implements OrderService {

    @Override
    public Order findById(Integer id) {
        return new Order(id, "ocean_order", 19.21);
    }
}
