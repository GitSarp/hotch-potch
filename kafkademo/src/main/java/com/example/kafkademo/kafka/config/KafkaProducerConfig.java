package com.example.kafkademo.kafka.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.util.unit.DataSize;

import java.util.HashMap;
import java.util.Map;

/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 4:12 PM 2019/3/4
 * @ Description：
 * @ Modified By：
 */
@Configuration
@EnableKafka
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;

    @Value("${spring.kafka.producer.retries}")
    private int retries;

    @Value("${spring.kafka.producer.batch-size}")
    private int batchSize;

    @Value("${spring.kafka.producer.buffer-memory}")
    private int bufferMemory;

    @Value("${spring.kafka.producer.properties.linger.ms}")
    private int linger;

    @Value("${spring.kafka.producer.acks}")
    private String acks;

    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;

    public Map<String,Object> producerConfig(){
        Map<String,Object> propsMap = new HashMap<>();
        propsMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,servers);
        propsMap.put(ProducerConfig.BUFFER_MEMORY_CONFIG,bufferMemory);
        propsMap.put(ProducerConfig.BATCH_SIZE_CONFIG,batchSize);
        propsMap.put(ProducerConfig.RETRIES_CONFIG,retries);
        propsMap.put(ProducerConfig.LINGER_MS_CONFIG,linger);
        propsMap.put(ProducerConfig.ACKS_CONFIG,acks);
        propsMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        propsMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,valueSerializer);
        return  propsMap;
    }

    public ProducerFactory<String,String> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String,String> kafkaTemplate(){
        KafkaTemplate<String,String> kafkaTemplate = new KafkaTemplate<>(producerFactory());
//        kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
//            @Override
//            public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata) {
//                System.out.println("send success=>"+"topic:"+topic+",partition:"+partition+",key:"+key+",value:"+value);
//            }
//
//            @Override
//            public void onError(String topic, Integer partition, String key, String value, Exception exception) {
//                System.out.println("send failed=>"+"topic:"+topic+",partition:"+partition+",key:"+key+",value:"+value);
//            }
//
//        });
        return kafkaTemplate;
    }
}
