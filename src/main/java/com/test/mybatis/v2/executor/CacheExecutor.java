package com.test.mybatis.v2.executor;

import com.test.mybatis.v2.cache.CacheKey;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 缓存的Executor，用了装饰器模式
 * @author: linyh
 * @create: 2018-11-01 18:02
 **/
public class CacheExecutor implements CustomExecutor{

    //这里的delegate为被装饰的executor
    private CustomExecutor delegate;
    private static final Map<Integer, Object> cache = new HashMap<>();

    /**
     * 装饰executor，为了在原有的executor之上增加新的功能，扩展为意图
     */
    public CacheExecutor(CustomExecutor delegate) {
        this.delegate = delegate;
    }

    /**
     * 修改了查询方法，扩展了缓存功能。
     * 由于只实现了查询功能，所以这里的缓存处理没有清空的处理。
     * 后续升级版本时需要新增缓存更新方法。
     */
    @Override
    public <T> T query(String statement, String parameter, Class pojo) throws IllegalAccessException, SQLException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        //这里的CacheKey为缓存的关键
        CacheKey cacheKey = new CacheKey();
        //根据SQL语句与参数两个维度来判断每次查询是否相同
        //底层原理为hashCode的计算，同一个SQL语句与同一个参数将视为同一个查询，cacheKey中的code也会一样
        //在Mybatis中的cacheKey里的code维度有更多，这里简化体现思想即可
        cacheKey.update(statement);
        cacheKey.update(parameter);

        //判断Map中是否含有根据SQL语句与参数算出来的code
        if (!cache.containsKey(cacheKey.getCode())) {
            //如没有就用被装饰者的查询方法，然后新增缓存
            Object obj = delegate.query(statement, parameter, pojo);
            cache.put(cacheKey.getCode(), obj);
            return (T)obj;
        }
        //缓存命中
        System.out.println("从缓存拿数据" + cache.get(cacheKey.getCode()));
        return (T)cache.get(cacheKey.getCode());
    }
}
