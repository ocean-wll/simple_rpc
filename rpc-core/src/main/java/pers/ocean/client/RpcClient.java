package pers.ocean.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import okhttp3.MediaType;
import pers.ocean.api.*;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * rpc客户端
 *
 * @author ocean_wll
 * @date 2021/6/29
 */
public class RpcClient {

    static {
        // fastjson 解析加白
        ParserConfig.getGlobalInstance().addAccept("pers.ocean");
    }

    public static <T, filters> T createFromRegistry(final Class<T> serviceName, final String zkUrl, Router router,
                                                    LoadBalancer loadBalancer, Filter filter) {
        List<String> invokers = new ArrayList<>();
        // TODO ocean_wll 所有的服务列表可以从zk中获取
        List<String> urls = router.route(invokers);

        String url = loadBalancer.select(urls);
        return (T) create(serviceName, url, filter);

    }

    public static <T> T create(final Class<T> serviceName, final String url, Filter... filters) {
        // TODO ocean_wll 可以改成AOP实现
        return (T) Proxy.newProxyInstance(RpcClient.class.getClassLoader(), new Class[]{serviceName},
                new RpcInvocationHandler(serviceName, url, filters));
    }


    public static class RpcInvocationHandler implements InvocationHandler {

        public static final MediaType JSON_TYPE = MediaType.get("application/json; charset=utf-8");

        private final Class<?> serviceClass;
        private final String url;
        private final Filter[] filters;

        public <T> RpcInvocationHandler(Class<T> serviceClass, String url, Filter... filters) {
            this.serviceClass = serviceClass;
            this.url = url;
            this.filters = filters;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            RpcRequest request = new RpcRequest();
            request.setServiceName(this.serviceClass.getName());
            request.setMethod(method.getName());
            request.setParams(args);

            if (filters != null) {
                for (Filter filter : filters) {
                    if (!filter.filter(request)) {
                        return null;
                    }
                }
            }

            RpcResponse response = post(request, url);
            if (!response.getSuccess()) {
                return response.getException();
            }
            return JSON.parse(response.getResult().toString());
        }

        /**
         * 发起http请求调用rpc
         *
         * @param req 请求对象
         * @param url 目标地址
         * @return RpcResponse
         */
        private RpcResponse post(RpcRequest req, String url) throws IOException, URISyntaxException, InterruptedException {
            // TODO ocean_wll 发起http请求，这块可以改成netty通讯
//            String reqJson = JSON.toJSONString(req);
//            OkHttpClient client = new OkHttpClient();
//            final Request request = new Request.Builder()
//                    .url(url)
//                    .post(RequestBody.create(JSON_TYPE, reqJson))
//                    .build();
//            String respJson = client.newCall(request).execute().body().string();
            NettyHttpClient client = new NettyHttpClient();
            String respJson = (String) client.sendRequest(url, req);
            return JSON.parseObject(respJson, RpcResponse.class);
        }
    }
}
