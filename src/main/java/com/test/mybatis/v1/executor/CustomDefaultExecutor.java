package com.test.mybatis.v1.executor;

import com.test.mybatis.v1.mapper.Test;

import java.sql.*;

/**
 * @description: 自定义Executor
 * @author: linyh
 * @create: 2018-10-31 17:46
 **/
public class CustomDefaultExecutor implements CustomExecutor{

    @Override
    public <T> T query(String statement, String parameter) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Test test = null;
        try {
            conn = getConnection();

            //TODO ParameterHandler
            preparedStatement = conn.prepareStatement(String.format(statement, Integer.parseInt(parameter)));
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getResultSet();

            //TODO ObjectFactory
            test = new Test();

            //TODO ResultSetHandler
            while (rs.next()) {
                test.setId(rs.getInt(1));
                test.setNums(rs.getInt(2));
                test.setName(rs.getString(3));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return (T)test;
    }

    public Connection getConnection() throws SQLException {
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
