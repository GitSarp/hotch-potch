package com.example.kafkademo.kafka;

import java.util.Map;

import com.example.kafkademo.kafka.util.BeanUtil;
import org.apache.kafka.common.serialization.Serializer;

/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 4:37 PM 2019/3/11
 * @ Description：
 * @ Modified By：
 */
public class KafkaEncoding implements Serializer<Object> {
    @Override
    public void configure(Map configs, boolean isKey) {

    }
    @Override
    public byte[] serialize(String topic, Object data) {
        return BeanUtil.bean2Byte(data);
    }

    @Override
    public void close() {
        System.out.println("KafkaEncoding is close");
    }
}
