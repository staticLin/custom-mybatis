package com.test.mybatis.v2.executor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @description: 负责结果的映射
 * @author: linyh
 * @create: 2018-11-02 10:10
 **/
public class ResultSetHandler {

    /**
     * 细化了executor的职责，专门负责结果映射
     */
    public <T> T handle(ResultSet resultSet, Class pojo) throws IllegalAccessException, InstantiationException, NoSuchMethodException, SQLException, InvocationTargetException {
        //代替了ObjectFactory
        Object pojoObj = pojo.newInstance();

        //遍历pojo中每一个属性域去设置参数
        if (resultSet.next()) {
            for (Field field : pojoObj.getClass().getDeclaredFields()) {
                setValue(pojoObj, field, resultSet);
            }
        }
        return (T)pojoObj;
    }

    /**
     * 利用反射给每一个属性设置参数
     */
    private void setValue(Object pojo, Field field, ResultSet rs) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SQLException {
        Method setMethod = pojo.getClass().getMethod("set" + firstWordCapital(field.getName()), field.getType());
        setMethod.invoke(pojo, getResult(rs, field));
    }

    /**
     * 根据反射判断类型，从ResultSet中取对应类型参数
     */
    private Object getResult(ResultSet rs, Field field) throws SQLException {
        //TODO 这里需要用TypeHandle处理，偷懒简化了，后续升级点
        Class type = field.getType();
        if (Integer.class == type) {
            return rs.getInt(field.getName());
        }
        if (String.class == type) {
            return rs.getString(field.getName());
        }
        return rs.getString(field.getName());
    }

    /**
     * 将一个单词首字母大写
     */
    private String firstWordCapital(String word){
        String first = word.substring(0, 1);
        String tail = word.substring(1);
        return first.toUpperCase() + tail;
    }
}
