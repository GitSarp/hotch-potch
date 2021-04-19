package com.example.dynamicdatasource;

import com.example.dynamicdatasource.context.JdbcContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: freaxjj
 * @Desc:
 * @Date: 4/15/21 3:39 PM
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 保存之前的多数据源，以便动态调整
     */
    private Map<Object, Object> dynamicTargetDataSources = new HashMap<>();


    /**
     * 即每次想切换数据的时候修改CurrentLookupKey，这样就能找到该key对应的数据源
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        //log.info("数据源为{}", JdbcContextHolder.getCurrentTenant());
        return JdbcContextHolder.getCurrentTenant();
    }


    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        //保存之前的多数据源，以便动态调整
        this.dynamicTargetDataSources = targetDataSources;

        refreshDataSources(targetDataSources);
    }

    /**
     * 刷新数据源
     * @param targetDataSources
     */
    private void refreshDataSources(Map<Object, Object> targetDataSources){
        super.setTargetDataSources(targetDataSources);
        //必须有！动态修改配置才能生效
        super.afterPropertiesSet();
    }

    /**
     * 新增数据源
     * @param key 数据源标识
     * @param dataSource 数据源
     */
    public void addTargetDataSource(Object key, Object dataSource) {
        dynamicTargetDataSources.put(key, dataSource);
        refreshDataSources(dynamicTargetDataSources);
    }
}
