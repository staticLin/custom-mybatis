package com.test.mybatis.v2.plugin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description: 插件代理者
 * @author: linyh
 * @create: 2018-11-02 10:30
 **/
public class Plugin implements InvocationHandler {
    private Object target;
    private Interceptor interceptor;

    /**
     * @param target 被代理的Executor类
     * @param interceptor plugin插件
     */
    public Plugin(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    /**
     * 包装executor的方法
     * @param obj 被代理的Executor类
     * @param interceptor plugin插件
     * @return 代理类
     */
    public static Object wrap(Object obj, Interceptor interceptor) {
        Class clazz = obj.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new Plugin(obj, interceptor));
    }

    /**
     * 代理方法核心
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //判断插件上的注解是否与method的方法名匹配（需拦截executor的哪个一个方法）
        if (interceptor.getClass().isAnnotationPresent(com.test.mybatis.v2.annotation.Method.class)) {
            if (method.getName().equals(interceptor.getClass().getAnnotation(com.test.mybatis.v2.annotation.Method.class).value())) {
                //不执行原方法，直接执行插件方法
                return interceptor.intercept(new Invocation(target, method, args));
            }
        }
        return method.invoke(target, method, args);
    }
}
