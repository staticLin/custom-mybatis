package com.test.mybatis.v2;

import com.test.mybatis.v2.mapper.Test;
import com.test.mybatis.v2.mapper.TestCustomMapper;
import com.test.mybatis.v2.session.CustomSqlSession;
import com.test.mybatis.v2.session.SqlSessionFactory;

/**
 * @description:
 * @author: linyh
 * @create: 2018-10-31 18:05
 **/
public class TestMybatis {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

//        SqlSessionFactory factory = new SqlSessionFactory();
//        //没有插件且没有开启缓存
//        CustomSqlSession sqlSession1 = factory.build("com.test.mybatis.v2.mapper").openSqlSession();
//        TestCustomMapper mapper1 = sqlSession1.getMapper(TestCustomMapper.class);
//        Test test1 = mapper1.selectByPrimaryKey(1);
//        System.out.println("第一次查询结果为: " + test1);
//        test1 = mapper1.selectByPrimaryKey(1);
//        System.out.println("第二次查询结果为: " + test1);

//        SqlSessionFactory factory = new SqlSessionFactory();
//        //有插件但没有开启缓存
//        CustomSqlSession sqlSession2 = factory.build("com.test.mybatis.v2.mapper",
//                new String[] {"com.test.mybatis.v2.plugin.customPlugin.testPlugin"}).openSqlSession();
//        TestCustomMapper mapper2 = sqlSession2.getMapper(TestCustomMapper.class);
//        Test test2 = mapper2.selectByPrimaryKey(2);
//        System.out.println("第一次查询结果为: " + test2);
//        test2 = mapper2.selectByPrimaryKey(2);
//        System.out.println("第二次查询结果为: " + test2);

        SqlSessionFactory factory = new SqlSessionFactory();
        //有插件且有缓存
        CustomSqlSession sqlSession3 = factory.build("com.test.mybatis.v2.mapper",
                new String[] {"com.test.mybatis.v2.plugin.customPlugin.testPlugin"}, true).openSqlSession();
        TestCustomMapper mapper3 = sqlSession3.getMapper(TestCustomMapper.class);
        Test test3 = mapper3.selectByPrimaryKey(3);
        System.out.println("第一次查询结果为: " + test3);
        test3 = mapper3.selectByPrimaryKey(3);
        System.out.println("第二次查询结果为: " + test3);
    }
}
