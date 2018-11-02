package com.test.mybatis.v1.executor;

/**
 * @description:
 * @author: linyh
 * @create: 2018-10-31 16:32
 **/
public interface CustomExecutor {
    <T> T query(String statement, String parameter);
}
