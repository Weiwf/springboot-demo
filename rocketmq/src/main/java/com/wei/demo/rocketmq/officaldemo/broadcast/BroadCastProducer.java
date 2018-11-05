package com.wei.demo.rocketmq.officaldemo.broadcast;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @Author: weiwenfeng
 * @Date: 2018/11/2
 */
public class BroadCastProducer {

    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("default-broadcast");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message = new Message("broadcast-topic",("message:" + i).getBytes());
            SendResult sendResult = producer.send(message);
            System.out.println(sendResult);
        }
        producer.shutdown();
    }
}
