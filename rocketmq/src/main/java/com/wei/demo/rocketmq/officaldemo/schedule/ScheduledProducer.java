package com.wei.demo.rocketmq.officaldemo.schedule;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @Author: weiwenfeng
 * @Date: 2018/11/5
 */
public class ScheduledProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        for (int i =0 ;i < 10;i++){
            Message message = new Message("ScheduleTopic",null,null,("msg" + i).getBytes());
            //messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            //上述level可在broker配置文件中自定义配置
            //level=0 级表示不延时，level=1 表示延时1s，level=2 表示延时5s...
            message.setDelayTimeLevel(3);
            producer.send(message);
        }
        producer.shutdown();
    }
}
