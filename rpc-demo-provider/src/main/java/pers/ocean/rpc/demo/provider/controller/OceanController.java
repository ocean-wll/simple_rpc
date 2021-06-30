package pers.ocean.rpc.demo.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.ocean.api.RpcRequest;
import pers.ocean.api.RpcResponse;
import pers.ocean.server.RpcInvoker;

/**
 * 测试入口
 *
 * @author ocean_wll
 * @date 2021/6/30
 */
@RestController
public class OceanController {

    @Autowired
    private RpcInvoker rpcInvoker;

    @PostMapping("/")
    public RpcResponse invoke(@RequestBody RpcRequest rpcRequest) {
        return rpcInvoker.invoke(rpcRequest);
    }
}
