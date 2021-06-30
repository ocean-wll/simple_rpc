package pers.ocean.api;

/**
 * 过滤器接口
 *
 * @author ocean_wll
 * @date 2021/6/29
 */
public interface Filter {

    /**
     * 过滤
     *
     * @param request rpc请求
     * @return true通过，false不通过
     */
    Boolean filter(RpcRequest request);
}
