package pers.ocean.rpc.demo.provider.service;

import pers.ocean.rpc.demo.api.model.User;
import pers.ocean.rpc.demo.api.service.UserService;

/**
 * 用户接口实现类
 *
 * @author ocean_wll
 * @date 2021/6/30
 */
public class UserServiceImpl implements UserService {

    @Override
    public User findById(Integer id) {
        return new User(id, "ocean");
    }
}
