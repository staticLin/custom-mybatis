package com.test.mybatis.v2.executor;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @description: 负责查询工作
 * @author: linyh
 * @create: 2018-11-02 10:10
 **/
public class StatementHandler {

    private ResultSetHandler resultSetHandler = new ResultSetHandler();

    /**
     * 主要执行查询工作的地方
     */
    public <T> T query(String statement, String parameter, Class pojo) throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Connection conn = null;

        //TODO 这里需要一个ParameterHandler,然后用TypeHandler设置参数，偷懒没写TypeHandler...
        statement = String.format(statement, Integer.parseInt(parameter));

        try {
            conn = getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.execute();
            //查询到这里结束，映射结果的工作委派给ResultSetHandler去做
            return resultSetHandler.handle(preparedStatement.getResultSet(), pojo);
        } finally {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        }
    }

    private Connection getConnection() throws SQLException {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/gp?serverTimezone=UTC";
        String username = "root";
        String password = "admin";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
