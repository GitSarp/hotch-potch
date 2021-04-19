package com.example.dynamicdatasource.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.example.dynamicdatasource.DynamicDataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: freaxjj
 * @Desc: 多数据源配置
 * @Date: 4/15/21 5:26 PM
 */
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class DataSourceConfig {
//    @Bean("dynamic1")
//    @ConfigurationProperties(prefix = "spring.datasource.dynamic1")
//    public DataSource dataSource1(){
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean("dynamic2")
//    @ConfigurationProperties(prefix = "spring.datasource.dynamic2")
//    public DataSource dataSource2(){
//        return DataSourceBuilder.create().build();
//    }

    private Map<String, DataSourceProperty> dynamic;

    //第一个数据库作为默认数据库 TODO 需考虑
    private boolean firstDefaultDB = true;

    @Bean("dynamicDataSource")
    public DynamicDataSource dynamicDataSource(){
        log.info("======初始化动态数据源=====");
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        //配置多数据源
        Map<Object, Object> map = new HashMap<>();
        dynamic.entrySet().forEach(db -> {
            //创建数据源
            DataSourceProperty propertie = db.getValue();
            DataSource dataSource = createDataSource(propertie);
            //设置第一个数据库为默认数据源
            if(firstDefaultDB){
                dynamicDataSource.setDefaultTargetDataSource(dataSource);
                firstDefaultDB = false;
            }
            //key需要跟ThreadLocal中的值对应,即租户id或名称
            map.put(db.getKey(), dataSource);
        });
        //设置数据源
        dynamicDataSource.setTargetDataSources(map);
        log.info("======成功初始化动态数据源=====");
        return dynamicDataSource;
    }

    @Bean
    public MybatisSqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
        MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
        /**
         * 重点，使分页插件生效
         */
        Interceptor[] plugins = new Interceptor[1];
        plugins[0] = paginationInterceptor();
        sessionFactory.setPlugins(plugins);

        //配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource作为数据源则不能实现切换
        sessionFactory.setDataSource(dynamicDataSource());
        // 扫描Model
        sessionFactory.setTypeAliasesPackage("com.example.dynamicdatasource.entity");
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 扫描映射文件
        sessionFactory.setMapperLocations(resolver.getResources("classpath*:mapper/*.xml"));
        return sessionFactory;
    }
    @Bean
    public PlatformTransactionManager transactionManager() {
        // 配置事务管理, 使用事务时在方法头部添加@Transactional注解即可
        return new DataSourceTransactionManager(dynamicDataSource());
    }
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        // 攻击 SQL 阻断解析器、加入解析链
        sqlParserList.add(new BlockAttackSqlParser());
        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;
    }

    /**
     * TODO 创建数据库，只有基本的属性
     * @return
     */
    public static DataSource createDataSource(DataSourceProperty propertie){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(propertie.getDriverClassName());
        dataSourceBuilder.url(propertie.getUrl());
        dataSourceBuilder.username(propertie.getUsername());
        dataSourceBuilder.password(propertie.getPassword());
        dataSourceBuilder.type(propertie.getType());
        return dataSourceBuilder.build();
    }
}
