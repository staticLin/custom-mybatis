package com.test.mybatis.v2.cache;

import java.lang.reflect.Array;

/**
 * @description: 缓存key值
 * @author: linyh
 * @create: 2018-11-02 11:22
 **/
public class CacheKey {
    //沿用MyBatis设计的默认值
    private static final int DEFAULT_HASHCODE = 17;
    private static final int DEFAULT_MULTIPLYER = 37;

    private int hashCode;
    private int count;
    private int multiplyer;

    public CacheKey() {
        this.hashCode = DEFAULT_HASHCODE;
        this.count = 0;
        this.multiplyer = DEFAULT_MULTIPLYER;
    }

    /**
     * 计算每一个传进来的Object的独有的一个code
     */
    public void update(Object object){
        if (object != null && object.getClass().isArray()) {
            int length = Array.getLength(object);
            for (int i = 0; i < length; i++) {
                Object element = Array.get(object, i);
                doUpdate(element);
            }
        }else {
            doUpdate(object);
        }
    }

    public int getCode() {
        return hashCode;
    }

    private void doUpdate(Object o) {
        int baseHashCode = o == null ? 1 : o.hashCode();
        count++;
        baseHashCode *= count;

        hashCode = multiplyer * hashCode + baseHashCode;
    }
}
