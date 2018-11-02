package com.test.mybatis.v2.annotation;

import java.lang.annotation.*;

/**
 * @Author:linyh
 * @Date: 2018/11/1 17:37
 * @Modified By:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Select {
    String value();
}
