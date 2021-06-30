package pers.ocean.api;

import java.util.List;

/**
 * 路由器
 *
 * @author ocean_wll
 * @date 2021/6/29
 */
public interface Router {

    /**
     * url路由地址
     *
     * @param urls 源地址
     * @return 路由后的地址
     */
    List<String> route(List<String> urls);
}
