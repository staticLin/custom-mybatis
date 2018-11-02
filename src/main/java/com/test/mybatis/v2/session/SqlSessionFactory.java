package com.test.mybatis.v2.session;

/**
 * @description: 创建SqlSession工厂
 * @author: linyh
 * @create: 2018-11-01 17:49
 **/
public class SqlSessionFactory {

    private CustomConfiguration configuration;

    /**
     * 以下build方法将初始化Factory的属性Configuration，具体工作在Configuration构造器中完成
     */
    public SqlSessionFactory build(String mapperPath) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return this.build(mapperPath, null, false);
    }

    public SqlSessionFactory build(String mapperPath, String[] pluginPath) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return this.build(mapperPath, pluginPath, false);
    }

    public SqlSessionFactory build(String mapperPath, String[] pluginPath, boolean enableCache) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        configuration = new CustomConfiguration(mapperPath, pluginPath, enableCache);
        return this;
    }

    /**
     * 根据配置信息(Configuration)获取对应的SqlSession
     */
    public CustomSqlSession openSqlSession(){
        return new CustomSqlSession(configuration);
    }
}
