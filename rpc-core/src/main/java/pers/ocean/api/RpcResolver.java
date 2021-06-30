package pers.ocean.api;

/**
 * 处理器
 *
 * @author ocean_wll
 * @date 2021/6/29
 */
public interface RpcResolver {

    /**
     * 查找服务
     *
     * @param serviceName 服务名
     * @return 服务对象
     */
    Object resolver(String serviceName);
}
