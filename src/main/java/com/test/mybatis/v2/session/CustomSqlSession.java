package com.test.mybatis.v2.session;

import com.test.mybatis.v2.executor.CustomExecutor;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * @description: 自定义的SqlSession
 * @author: linyh
 * @create: 2018-10-31 16:31
 **/
public class CustomSqlSession {

    //持有两个关键对象
    private CustomConfiguration configuration;
    private CustomExecutor executor;

    /**
     * 用构造器将两个对象形成关系
     */
    public CustomSqlSession(CustomConfiguration configuration) {
        this.configuration = configuration;
        //这里需要决定是否开启缓存，则从Configuraton中判断是否需要缓存，创建对应Executor
        this.executor = configuration.newExecutor();
    }

    public CustomConfiguration getConfiguration() {
        return configuration;
    }

    /**
    * 委派configuration获取mapper
    */
    public <T> T getMapper(Class<T> clazz){
        return configuration.getMapper(clazz, this);
    }

    /**
     * 委派executor查询
     */
    public <T> T selectOne(String statement, String parameter, Class pojo) throws IllegalAccessException, SQLException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        return executor.query(statement, parameter, pojo);
    }
}
