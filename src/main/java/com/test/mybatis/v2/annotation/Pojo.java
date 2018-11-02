package com.test.mybatis.v2.annotation;

import java.lang.annotation.*;

/**
 * @Author:linyh
 * @Date: 2018/11/1 17:40
 * @Modified By:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Pojo {
    Class<?> value();
}
