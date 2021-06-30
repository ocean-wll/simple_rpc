package pers.ocean.api;

import java.util.List;

/**
 * 负载均衡器
 *
 * @author ocean_wll
 * @date 2021/6/29
 */
public interface LoadBalancer {

    /**
     * 负载均衡
     *
     * @param urls 源地址集合
     * @return 需要调用的服务地址
     */
    String select(List<String> urls);
}
