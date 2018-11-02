package com.test.mybatis.v2.binding;

import com.test.mybatis.v2.session.CustomSqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @description: Mapper动态代理者
 * @author: linyh
 * @create: 2018-10-31 16:52
 **/
public class MapperProxy implements InvocationHandler{

    private CustomSqlSession sqlSession;
    private Class object;

    public MapperProxy(CustomSqlSession sqlSession, Class object) {
        this.sqlSession = sqlSession;
        this.object = object;
    }

    /**
     * 每一个Mapper的每个方法都将执行invoke方法，此方法判断方法名是否维护在Configuration中，如有则取出SQL
    */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (sqlSession.getConfiguration().hasStatement(method.getDeclaringClass().getName()+"."+method.getName())) {
            String sql = sqlSession.getConfiguration().getMappedStatement(method.getDeclaringClass().getName()+"."+method.getName());
            return sqlSession.selectOne(sql, args[0].toString(), object);
        }
        return method.invoke(proxy, args);
    }
}
