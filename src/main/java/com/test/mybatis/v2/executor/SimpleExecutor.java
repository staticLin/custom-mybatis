package com.test.mybatis.v2.executor;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

/**
 * @description: 自定义Executor
 * @author: linyh
 * @create: 2018-10-31 17:46
 **/
public class SimpleExecutor implements CustomExecutor {
    /**
     * 这里为默认Executor，细化了责任，查询交给StatementHandler处理
     */
    @Override
    public <T> T query(String statement, String parameter, Class pojo) throws IllegalAccessException, SQLException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        StatementHandler statementHandler = new StatementHandler();
        return statementHandler.query(statement, parameter, pojo);
    }
}
