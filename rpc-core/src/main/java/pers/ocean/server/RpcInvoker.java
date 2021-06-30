package pers.ocean.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import pers.ocean.api.RpcRequest;
import pers.ocean.api.RpcResolver;
import pers.ocean.api.RpcResponse;
import pers.ocean.exception.RpcException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * rpc调用
 *
 * @author ocean_wll
 * @date 2021/6/29
 */
public class RpcInvoker {

    private RpcResolver rpcResolver;

    public RpcInvoker(RpcResolver rpcResolver) {
        this.rpcResolver = rpcResolver;
    }

    /**
     * rpc调用
     *
     * @param request 请求对象
     * @return 方法处理结果
     */
    public RpcResponse invoke(RpcRequest request) {

        // 查找服务提供方 TODO ocean_wll 改成泛型和反射 或者 使用字节码生成技术
        Object service = rpcResolver.resolver(request.getServiceName());
        if (service == null) {
            return RpcResponse.buildFail(new RpcException("not find service"));
        }

        // 获取参数类型数组
        Class<?>[] parameterTypes;
        if (request.getParams() != null) {
            parameterTypes = new Class[request.getParams().length];
            for (int i = 0; i < request.getParams().length; i++) {
                parameterTypes[i] = request.getParams()[i].getClass();
            }
        } else {
            parameterTypes = new Class[0];
        }

        try {
            Method method = resolveMethodFromClass(service.getClass(), request.getMethod(), parameterTypes);
            Object result = method.invoke(service, request.getParams());
            // 将结果序列化并返回,附带上class的全名
            return RpcResponse.buildSuccess(JSON.toJSONString(result, SerializerFeature.WriteClassName));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            return RpcResponse.buildFail(new RpcException(e.getMessage()));
        }
    }

    /**
     * 获取服务调用方法
     *
     * @param klass          服务提供类
     * @param methodName     需要调用的方法名
     * @param parameterTypes 方法参数类型数组
     * @return Method对象
     * @throws NoSuchMethodException
     */
    private Method resolveMethodFromClass(Class<?> klass, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        return klass.getMethod(methodName, parameterTypes);
    }
}
