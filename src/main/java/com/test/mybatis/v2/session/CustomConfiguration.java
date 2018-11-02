package com.test.mybatis.v2.session;

import com.test.mybatis.v2.TestMybatis;
import com.test.mybatis.v2.annotation.Pojo;
import com.test.mybatis.v2.annotation.Select;
import com.test.mybatis.v2.binding.MapperRegistory;
import com.test.mybatis.v2.executor.CacheExecutor;
import com.test.mybatis.v2.executor.SimpleExecutor;
import com.test.mybatis.v2.executor.CustomExecutor;
import com.test.mybatis.v2.plugin.CustomInterceptorChain;
import com.test.mybatis.v2.plugin.Interceptor;
import org.apache.ibatis.annotations.Delete;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: linyh
 * @create: 2018-10-31 16:32
 **/
public class CustomConfiguration {

    public static final MapperRegistory mapperRegistory = new MapperRegistory();
    public static final Map<String, String> mappedStatements = new HashMap<>();

    private CustomInterceptorChain interceptorChain = new CustomInterceptorChain();
    private boolean enableCache = false;
    private List<Class<?>> mapperList = new ArrayList<>();
    private List<String> classPaths = new ArrayList<>();

    /**
     * 初始化时Configuration加载所有Mapper信息、plugin信息、缓存是否开启信息
     */
    public CustomConfiguration(String mapperPath, String[] pluginPath, boolean enableCache) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //扫描mapper路径，将必要的mapper信息存入mapperRegistory与mapperStatements
        scanPackage(mapperPath);
        for (Class<?> mapper : mapperList) {
            //当类为接口时视其为mapper，开始解析它
            //Myabtis中判断是否为mapper还用到了isIndependent的方法判断，较为复杂，这里简化，体现思想即可
            if (mapper.isInterface()) {
                parsingClass(mapper);
            }
        }
        if (pluginPath != null) {
            //遍历plugin路径，初始化plugin并放入list中
            for (String plugin : pluginPath) {
                Interceptor interceptor =  (Interceptor) Class.forName(plugin).newInstance();
                interceptorChain.addInterceptor(interceptor);
            }
        }
        //设置缓存是否开启
        this.enableCache = enableCache;
    }

    /**
     * MapperProxy根据statementName查找是否有对应SQL
     */
    public boolean hasStatement(String statementName) {
        return mappedStatements.containsKey(statementName);
    }

    /**
     * MapperProxy根据statementID获取SQL
     */
    public String getMappedStatement(String id) {
        return mappedStatements.get(id);
    }

    public <T> T getMapper(Class<T> clazz, CustomSqlSession sqlSession) {
        return mapperRegistory.getMapper(clazz, sqlSession);
    }

    /**
     * 创建一个Executor(因为加入了plugin功能，需要判断是否创建带plugin的executor)
     */
    public CustomExecutor newExecutor() {
        CustomExecutor executor = createExecutor();
        if (interceptorChain.hasPlugin()) {
            return (CustomExecutor)interceptorChain.pluginAll(executor);
        }
        return executor;
    }

    /**
     * 创建一个Executor(需要判断是否创建带缓存功能的executor)
     */
    private CustomExecutor createExecutor(){
        if (enableCache) {
            return new CacheExecutor(new SimpleExecutor());
        }
        return new SimpleExecutor();
    }

    /**
     * 解析类中的注解
     */
    private void parsingClass(Class<?> mapper) {
        //如有Pojo注解，则可以获取到实体类的信息
        if (mapper.isAnnotationPresent(Pojo.class)) {
            for (Annotation annotation : mapper.getAnnotations()) {
                if (annotation.annotationType().equals(Pojo.class)) {
                    //将mapper与实体类信息注册进mapperRegistory中
                    mapperRegistory.addMapper(mapper, ((Pojo)annotation).value());
                }
            }
        }

        Method[] methods = mapper.getMethods();
        for (Method method : methods) {
            //TODO 新增Update、Delete、Insert注解
            //如果有Select注解就解析SQL语句
            if (method.isAnnotationPresent(Select.class)) {
                for (Annotation annotation : method.getDeclaredAnnotations()) {
                    if (annotation.annotationType().equals(Select.class)) {
                        //将方法名与SQL语句注册进mappedStatements中
                        mappedStatements.put(method.getDeclaringClass().getName() + "." +method.getName(),
                                ((Select) annotation).value());
                    }
                }
            }
        }
    }

    /**
     * 扫描包名，获取包下的所有.class文件
     */
    private void scanPackage(String mapperPath) throws ClassNotFoundException {
        String classPath = TestMybatis.class.getResource("/").getPath();
        mapperPath = mapperPath.replace(".", File.separator);
        String mainPath = classPath + mapperPath;
        doPath(new File(mainPath));
        for (String className : classPaths) {
            className = className.replace(classPath.replace("/","\\").replaceFirst("\\\\",""),"").replace("\\",".").replace(".class","");
            Class<?> clazz = Class.forName(className);
            mapperList.add(clazz);
        }
    }

    /**
     * 该方法会得到所有的类，将类的绝对路径写入到classPaths中
     */
    private void doPath(File file) {
        if (file.isDirectory()) {//文件夹
            //文件夹我们就递归
            File[] files = file.listFiles();
            for (File f1 : files) {
                doPath(f1);
            }
        } else {//标准文件
            //标准文件我们就判断是否是class文件
            if (file.getName().endsWith(".class")) {
                //如果是class文件我们就放入我们的集合中。
                classPaths.add(file.getPath());
            }
        }
    }
}
