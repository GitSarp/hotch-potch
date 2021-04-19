package com.example.dynamicdatasource.context;

import org.springframework.util.StringUtils;

/**
 * @Author: freaxjj
 * @Desc:
 * @Date: 4/15/21 4:03 PM
 */
public class JdbcContextHolder {

    //默认租户
    //todo 超管及默认租户设置
    public static final String DEFAULT_TENANT = "dynamic";


    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>() {
        /**
         * 将 master 数据源的 key作为默认数据源的 key
         */
        @Override
        protected String initialValue() {
            //todo 硬编码
            return DEFAULT_TENANT;
        }
    };

    /**
     * 数据源的 key集合，用于切换时判断数据源是否存在
     */
    //public static List<Object> dataSourceKeys = new ArrayList<>();


    /**
     * 设置默认租户
     */
    public static void setDefaultTenant() {
        setCurrentTenant(JdbcContextHolder.DEFAULT_TENANT);
    }

    /**
     * 切换数据源
     *
     * @param key 数据源
     */
    public static void setCurrentTenant(String key) {
        if (!StringUtils.isEmpty(key)) {
            contextHolder.set(key);
        }
    }

    /**
     * 获取数据源
     *
     * @return
     */
    public static String getCurrentTenant() {
        return contextHolder.get();
    }

    /**
     * 重置数据源
     */
    public static void clearDataSourceKey() {
        contextHolder.remove();
    }

    /**
     * 判断是否包含数据源
     *
     * @param key 数据源
     * @return
     */
//    public static boolean containDataSourceKey(String key) {
//        return dataSourceKeys.contains(key);
//    }

    /**
     * 添加数据源Keys
     *
     * @param keys
     * @return
     */
//    public static boolean addDataSourceKeys(Collection<? extends Object> keys) {
//        return dataSourceKeys.addAll(keys);
//    }
}
