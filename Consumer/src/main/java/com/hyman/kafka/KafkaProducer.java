package com.hyman.kafka;

import com.alibaba.fastjson.JSONObject;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Date;
import java.util.Properties;
import java.util.Random;


public class KafkaProducer extends Thread{

    public String topic;

    private KafkaProducer(String topic){
        super();
        this.topic = topic;
    }

    private Producer createProducer() {
        Properties props = new Properties();

        //配置value的序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");

        props.put("metadata.broker.list","192.168.132.129:9092");
        props.put("request.required.acks", "1");
        return new Producer<Integer,String>(new ProducerConfig(props));
    }

    @Override
    public void run(){

        Producer producer = createProducer();
//        JSONObject msgHeadObject = new JSONObject();
//        msgHeadObject.put("platform","1");
//        msgHeadObject.put("pbusi","2");
//        msgHeadObject.put("cbusi","3");

        JSONObject dataObject = new JSONObject();
        dataObject.put("data","测试数据");
        Random rnd = new Random();
        while(true){
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("head",msgHeadObject);
//            jsonObject.put("data",dataObject);

//            System.out.println("message:" + jsonObject.toString());
            long runtime = new Date().getTime();  
            String ip = "192.168.2." + rnd.nextInt(255); 
            String msg = runtime + ",www.example.com," + ip; 
            
            KeyedMessage<Integer, String> data = new KeyedMessage<Integer, String>(topic, msg);
            producer.send(data);
            //producer.send(new KeyedMessage<Integer,String>(topic,jsonObject.toString()));

            
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public static void main(String[] args){
        new KafkaProducer("test").start();
    }
}