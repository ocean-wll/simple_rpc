package pers.ocean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 服务调用方
 *
 * @author : ocean_wll
 * @since 2021/7/3
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Consumer {

    /**
     * 服务名
     *
     * @return rpc服务名称
     */
    String serviceName();

    /**
     * 服务分组
     *
     * @return 服务分组
     */
    String group() default "OCEAN";

    /**
     * 服务版本
     *
     * @return 服务版本
     */
    String version();
}
