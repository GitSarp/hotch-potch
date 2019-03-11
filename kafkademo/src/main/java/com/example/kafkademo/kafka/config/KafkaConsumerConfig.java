package com.example.kafkademo.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 4:36 PM 2019/3/4
 * @ Description：同一主题两个组不一样的消费，同一组的共同消费
 * @ Modified By：
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;

    @Value("${spring.kafka.consumer.enable-auto-commit}")
    private Boolean autoCommit;

    @Value("${spring.kafka.consumer.properties.session.timeout}")
    private int sessionTimeOut;

    @Value("${spring.kafka.consumer.auto-commit-interval}")
    private String autoCommitInterval;
    
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId1;

    @Value("${spring.kafka.consumer.group-id2}")
    private String groupId2;
    
    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;
    
    @Value("${spring.kafka.consumer.properties.concurrency}")
    private int concurrency;

    @Value("${spring.kafka.consumer.value-deserializer}")
    private String valueDeserializer;

    public Map<String,Object> commonConsumerConfig(){
        Map<String,Object> propsMap = new HashMap<>();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,servers);
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,autoCommit);
        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,sessionTimeOut);
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,autoCommitInterval);
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,autoOffsetReset);
        //每个批次最大获取数
        propsMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,1000);
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,valueDeserializer);
        return  propsMap;
    }

    public Map<String,Object> consumerConfig1(){
        Map<String,Object> propsMap = commonConsumerConfig();
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG,groupId1);
        return  propsMap;
    }

    public Map<String,Object> consumerConfig2(){
        Map<String,Object> propsMap = commonConsumerConfig();
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG,groupId2);
        return  propsMap;
    }

    @Bean
    public ConsumerFactory<String,String> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfig1());
    }

//    @Bean
//    public ConsumerFactory<String,String> consumerFactory2(){
//        return new DefaultKafkaConsumerFactory<>(consumerConfig2());
//    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String,String> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setBatchListener(true);
        return factory;
    }

//    @Bean
//    ConcurrentKafkaListenerContainerFactory<String,String> kafkaListenerContainerFactory2(){
//        ConcurrentKafkaListenerContainerFactory<String,String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory2());
//        factory.setBatchListener(true);
//        return factory;
//    }

    /**
     * 同一组共同消费同一主题
     * @param data
     * @param partitions
     * @param offsets
     */
    @KafkaListener(id = "test1",topics = "test",groupId = "${spring.kafka.consumer.group-id}")
    public void receive(List<String> data,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Integer> offsets){
        System.out.println("consumer1 consume message start...");
        for (int i = 0; i < data.size(); i++) {
            System.out.println("consumer1 consume message:" + data.get(i));
        }
        System.out.println("consumer1 consume message end...");

    }

    /**
     * 同一组共同消费同一主题
     * @param data
     * @param partitions
     * @param offsets
     */
    @KafkaListener(id = "test11",topics = "test",groupId = "${spring.kafka.consumer.group-id}")
    public void receive11(List<String> data,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Integer> offsets){
        System.out.println("consumer11 consume message start...");
        for (int i = 0; i < data.size(); i++) {
            System.out.println("consumer11 consume message:" + data.get(i));
        }
        System.out.println("consumer11 consume message end...");

    }

    /**
     * 同一组消费不同主题
     * @param data
     * @param partitions
     * @param offsets
     */
    @KafkaListener(id = "test12",topics = "test12",groupId = "${spring.kafka.consumer.group-id}")
    public void receive12(List<Object> data,
                          @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                          @Header(KafkaHeaders.OFFSET) List<Integer> offsets){
        System.out.println("consumer12 consume message start...");
        for (int i = 0; i < data.size(); i++) {
            System.out.println("consumer12 consume message:" + data.get(i));
        }
        System.out.println("consumer12 consume message end...");

    }

    /**
     * 不同组,同一主题  不一样的处理
     */
//    @KafkaListener(id = "test2",topics = "test",groupId = "${spring.kafka.consumer.group-id2}",containerFactory = "kafkaListenerContainerFactory2")
//    public void receive2(List<String> data,
//                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
//                        @Header(KafkaHeaders.OFFSET) List<Integer> offsets){
//        System.out.println("consumer3 start...");
//        for (int i = 0; i < data.size(); i++) {
//            System.out.println("consumer3 consume message:" + data.get(i));
//        }
//        System.out.println("consumer3 end...");
//    }

    /**
     * 不同组,同一主题  不一样的处理
     */
    @KafkaListener(id = "test2",topics = "test",groupId = "${spring.kafka.consumer.group-id2}")
    public void receive2(List<String> data,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Integer> offsets){
        System.out.println("consumer2 start...");
        for (int i = 0; i < data.size(); i++) {
            System.out.println("consumer2 consume message:" + data.get(i));
        }
        System.out.println("consumer2 end...");
    }
}
