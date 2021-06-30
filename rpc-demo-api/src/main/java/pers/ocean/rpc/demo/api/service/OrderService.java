package pers.ocean.rpc.demo.api.service;

import pers.ocean.rpc.demo.api.model.Order;

/**
 * 订单接口
 *
 * @author ocean_wll
 * @date 2021/6/30
 */
public interface OrderService {

    /**
     * 根据id查找对应的订单信息
     *
     * @param id 订单id
     * @return Order对象
     */
    Order findById(Integer id);
}
