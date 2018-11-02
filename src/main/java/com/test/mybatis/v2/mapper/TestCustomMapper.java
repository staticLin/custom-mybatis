package com.test.mybatis.v2.mapper;

import com.test.mybatis.v2.annotation.Pojo;
import com.test.mybatis.v2.annotation.Select;

/**
 * @Author:linyh
 * @Date: 2018/10/31 16:56
 * @Modified By:
 */
@Pojo(Test.class)
public interface TestCustomMapper {

    @Select("select * from test where id = %d")
    Test selectByPrimaryKey(int id);
}
