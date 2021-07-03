package pers.ocean.rpc.demo.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pers.ocean.rpc.demo.provider.netty.NettyServer;
import pers.ocean.server.RpcInvoker;

/**
 * @author ocean_wll
 * @date 2021/6/30
 */
@SpringBootApplication
public class RpcProviderApplication {

    private static RpcInvoker rpcInvoker;

    @Autowired
    public void setRpcInvoker(RpcInvoker rpcInvoker) {
        RpcProviderApplication.rpcInvoker = rpcInvoker;
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(RpcProviderApplication.class, args);

        NettyServer nettyServer = new NettyServer(rpcInvoker);
        nettyServer.run();
    }

}
