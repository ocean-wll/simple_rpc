package pers.ocean.rpc.demo.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单对象
 *
 * @author ocean_wll
 * @date 2021/6/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 订单名称
     */
    private String name;

    /**
     * 金额
     */
    private Double amount;
}
