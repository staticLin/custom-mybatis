package com.test.mybatis.v1.session;

import com.test.mybatis.v1.executor.CustomExecutor;

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
    public CustomSqlSession(CustomConfiguration configuration, CustomExecutor executor) {
        this.configuration = configuration;
        this.executor = executor;
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
    public <T> T selectOne(String statement, String parameter){
        return executor.query(statement, parameter);
    }
}
