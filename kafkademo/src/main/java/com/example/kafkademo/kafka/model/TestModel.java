package com.example.kafkademo.kafka.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 4:48 PM 2019/3/11
 * @ Description：
 * @ Modified By：
 */
@Data
public class TestModel implements Serializable {
    private int id;
    private String name;

    public TestModel(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
