package pers.ocean.api;

import lombok.Data;

/**
 * rpc请求对象
 *
 * @author ocean_wll
 * @date 2021/6/29
 */
@Data
public class RpcRequest {

    /**
     * 服务名
     */
    private String serviceName;

    /**
     * 调用方法
     */
    private String method;

    /**
     * 请求参数
     */
    private Object[] params;
}
