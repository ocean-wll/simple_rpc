package pers.ocean.rpc.demo.api.service;

import pers.ocean.rpc.demo.api.model.User;

/**
 * 用户接口
 *
 * @author ocean_wll
 * @date 2021/6/30
 */
public interface UserService {

    /**
     * 根据主键搜索用户信息
     *
     * @param id 用户id
     * @return User对象
     */
    User findById(Integer id);
}
