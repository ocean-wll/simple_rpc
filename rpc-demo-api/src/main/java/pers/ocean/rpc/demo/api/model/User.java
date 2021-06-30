package pers.ocean.rpc.demo.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体对象
 *
 * @author ocean_wll
 * @date 2021/6/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String name;
}
