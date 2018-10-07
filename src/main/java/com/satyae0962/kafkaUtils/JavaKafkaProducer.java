package com.satyae0962.kafkaUtils;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;


import java.util.Properties;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by satyanarayana on 30/09/18.
 */


public class JavaKafkaProducer {

    private final String topic = "tweets_spark";
    private final String zkQuorum = "localhost:2181";
    private final Logger log = Logger.getLogger(this.getClass().getName());
    public Properties KafkaInit(){
        Properties kafkaProp = new Properties();
        kafkaProp.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,zkQuorum);
        kafkaProp.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        kafkaProp.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        kafkaProp.put(ProducerConfig.CLIENT_ID_CONFIG,"Spark-Streaming-Tweets");
        log.info("Kafka Properties have been configured successfully");
        return kafkaProp;
    }



    public void KafkaTweetProducer(String tweet) {
        Properties kafkaProp = KafkaInit();
        System.out.println(tweet);
        final Producer<String,String> tweetProducer = new KafkaProducer<String, String>(kafkaProp);
        while(tweet!=null){
            UUID uuid = UUID.randomUUID();
            String KafkaKey = uuid.toString();
            ProducerRecord<String,String> kafkaRecord = new ProducerRecord<String, String>(topic,KafkaKey,tweet);

        }
    }
}
