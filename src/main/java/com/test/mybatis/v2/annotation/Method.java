package com.test.mybatis.v2.annotation;

import java.lang.annotation.*;

/**
 * @Author:linyh
 * @Date: 2018/11/2 10:38
 * @Modified By:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Method {
    String value();
}
