package pers.ocean.api;

import lombok.Data;
import pers.ocean.exception.RpcException;

/**
 * rpc响应结果
 *
 * @author ocean_wll
 * @date 2021/6/29
 */
@Data
public class RpcResponse {

    /**
     * 返回结果
     */
    private Object result;

    /**
     * 处理是否成功
     */
    private Boolean success;

    /**
     * 异常信息
     */
    private RpcException exception;

    /**
     * 构建成功对象
     *
     * @return RpcResponse
     */
    public static RpcResponse buildSuccess() {
        RpcResponse response = new RpcResponse();
        response.setSuccess(true);
        return response;
    }

    /**
     * 构建成功对象
     *
     * @param result 返回数据
     * @return RpcResponse
     */
    public static RpcResponse buildSuccess(Object result) {
        RpcResponse response = new RpcResponse();
        response.setSuccess(true);
        response.setResult(result);
        return response;
    }

    /**
     * 构建失败对象
     *
     * @return RpcResponse
     */
    public static RpcResponse buildFail() {
        RpcResponse response = new RpcResponse();
        response.setSuccess(false);
        return response;
    }

    /**
     * 构建失败对象
     *
     * @param e 异常信息
     * @return RpcResponse
     */
    public static RpcResponse buildFail(RpcException e) {
        RpcResponse response = new RpcResponse();
        response.setSuccess(false);
        response.setException(e);
        return response;
    }
}
