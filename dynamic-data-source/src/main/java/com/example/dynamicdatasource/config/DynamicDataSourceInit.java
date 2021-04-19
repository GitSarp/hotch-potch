//package com.example.dynamicdatasource;
//
//import com.zaxxer.hikari.HikariDataSource;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Author: freaxjj
// * @Desc: 从数据库初始化多数据源
// * @Date: 4/15/21 4:05 PM
// */
//@Slf4j
//@Configuration
//public class DynamicDataSourceInit {
//    @Autowired
//    private ITenantInfoService tenantInfoService;
//    @Bean
//    public void initDataSource() {
//        log.info("======初始化动态数据源=====");
//        DynamicDataSource dynamicDataSource = (DynamicDataSource) SpringContextUtils.getBean("dynamicDataSource");
//        HikariDataSource master = (HikariDataSource) SpringContextUtils.getBean("master");
//        Map<Object, Object> dataSourceMap = new HashMap<>();
//        dataSourceMap.put("master", master);
//        List<TenantInfo> tenantList = tenantInfoService.list();
//        for (TenantInfo tenantInfo : tenantList) {
//            log.info(tenantInfo.toString());
//            HikariDataSource dataSource = new HikariDataSource();
//            dataSource.setDriverClassName(tenantInfo.getDatasourceDriver());
//            dataSource.setJdbcUrl(tenantInfo.getDatasourceUrl());
//            dataSource.setUsername(tenantInfo.getDatasourceUsername());
//            dataSource.setPassword(tenantInfo.getDatasourcePassword());
//            dataSource.setDataSourceProperties(master.getDataSourceProperties());
//            dataSourceMap.put(tenantInfo.getTenantId(), dataSource);
//        }
//        // 设置数据源
//        dynamicDataSource.setDataSources(dataSourceMap);
//        /**
//         * 必须执行此操作，才会重新初始化AbstractRoutingDataSource 中的 resolvedDataSources，也只有这样，动态切换才会起效
//         */
//        dynamicDataSource.afterPropertiesSet();
//    }
//}
