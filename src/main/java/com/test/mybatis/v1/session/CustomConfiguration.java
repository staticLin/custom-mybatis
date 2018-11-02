package com.test.mybatis.v1.session;

import com.test.mybatis.v1.binding.MapperRegistory;
import com.test.mybatis.v1.mapper.TestCustomMapper;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: linyh
 * @create: 2018-10-31 16:32
 **/
public class CustomConfiguration {

    public final MapperRegistory mapperRegistory = new MapperRegistory();

    public static final Map<String, String> mappedStatements = new HashMap<>();

    //TODO 改用anontation扫描 (暂时HardCode)
    //初始化时Configuration加载所有Mapper方法与Sql语句
    public CustomConfiguration() {
        mapperRegistory.addMapper(TestCustomMapper.class);
        mappedStatements.put("com.test.mybatis.v1.mapper.TestCustomMapper.selectByPrimaryKey"
        , "select * from test where id = %d");
    }

    //MapperProxy根据statementName查找是否有对应SQL
    public boolean hasStatement(String statementName) {
        return mappedStatements.containsKey(statementName);
    }

    //MapperProxy根据statementID获取SQL
    public String getMappedStatement(String id) {
        return mappedStatements.get(id);
    }

    public <T> T getMapper(Class<T> clazz, CustomSqlSession sqlSession) {
        return mapperRegistory.getMapper(clazz, sqlSession);
    }
}
