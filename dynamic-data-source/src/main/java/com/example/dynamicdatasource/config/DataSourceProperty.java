package com.example.dynamicdatasource.config;

import lombok.Data;

import javax.sql.DataSource;

/**
 * @Author: freaxjj
 * @Desc:
 * @Date: 4/19/21 11:08 AM
 */
@Data
public class DataSourceProperty {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private Class<? extends DataSource> type;

    public void setType(String type) {
        try {
            this.type = (Class<? extends DataSource>) Class.forName(type);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
