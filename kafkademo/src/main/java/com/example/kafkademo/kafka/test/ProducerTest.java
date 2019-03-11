package com.example.kafkademo.kafka.test;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 11:23 AM 2019/3/11
 * @ Description：
 * @ Modified By：
 */
public class ProducerTest {
    private void execMsgSend() throws  Exception{
        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("acks", "1");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> procuder = new KafkaProducer<String, String>(props);

        String topic = "test";
        for (int i = 1; i <= 10; i++) {
            String value = " this is another message_" + i;
            ProducerRecord<String,String> record = new ProducerRecord<String, String>(topic,i+"",value);
            procuder.send(record,new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    System.out.println("message send to partition " + metadata.partition() + ", offset: " + metadata.offset());
                }
            });
            System.out.println(i+" ----   success");
            Thread.sleep(1000);
        }
        System.out.println("send message over.");
        procuder.close();
    }
    public static void main(String[] args) throws  Exception{
        ProducerTest test1 = new ProducerTest();
        test1.execMsgSend();
    }
}
