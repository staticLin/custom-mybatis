package com.test.mybatis.v2.plugin;

/**
 * @description:
 * @author: linyh
 * @create: 2018-11-02 10:12
 **/
public interface Interceptor {
    Object intercept(Invocation invocation) throws Throwable;

    Object plugin(Object target);
}
