package com.example.kafkademo.kafka;

import java.util.Map;
import com.example.kafkademo.kafka.util.BeanUtil;
import org.apache.kafka.common.serialization.Deserializer;

/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 4:35 PM 2019/3/11
 * @ Description：
 * @ Modified By：
 */
public class KafkaDecoding implements Deserializer<Object> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Object deserialize(String topic, byte[] data) {
        return BeanUtil.byte2Obj(data);
    }

    @Override
    public void close() {

    }
}
