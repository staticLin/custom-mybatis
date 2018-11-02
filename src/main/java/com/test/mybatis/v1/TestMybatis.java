package com.test.mybatis.v1;

import com.test.mybatis.v1.binding.MapperRegistory;
import com.test.mybatis.v1.executor.CustomDefaultExecutor;
import com.test.mybatis.v1.mapper.TestCustomMapper;
import com.test.mybatis.v1.session.CustomConfiguration;
import com.test.mybatis.v1.session.CustomSqlSession;

/**
 * @description:
 * @author: linyh
 * @create: 2018-10-31 18:05
 **/
public class TestMybatis {
    public static void main(String[] args) {
        CustomSqlSession sqlSession = new CustomSqlSession(
                new CustomConfiguration(), new CustomDefaultExecutor());
        TestCustomMapper testCustomMapper = sqlSession.getMapper(TestCustomMapper.class);
        System.out.println(testCustomMapper.selectByPrimaryKey(2));
    }
}
