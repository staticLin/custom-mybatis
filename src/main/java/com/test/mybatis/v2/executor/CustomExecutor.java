package com.test.mybatis.v2.executor;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * @description:
 * @author: linyh
 * @create: 2018-10-31 16:32
 **/
public interface CustomExecutor {
    <T> T query(String statement, String parameter, Class pojo) throws IllegalAccessException, SQLException, InstantiationException, InvocationTargetException, NoSuchMethodException;
}
