package com.wei.demo.rocketmq.rocketmq.blogdemo.simple;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

/**
 * @Author: weiwenfeng
 * @Date: 2018/9/17
 */
public class OrderProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("producer-order-group"); //参数为组名
        producer.setNamesrvAddr("127.0.0.1:9876");//指定nameserver地址
        producer.setRetryTimesWhenSendFailed(5); //消息发送失败重试次数
        producer.start();

        String[] tags = new String[]{"createTag", "payTag", "sendTag"};
        for (int orderId = 0; orderId < 2; orderId++) {
            for (int type = 0; type < 3; type++) {
                Message msg = new Message("orderTopic",
                        tags[type % tags.length],
                        orderId + ":" + type,
                        (orderId + ":" + type).getBytes());
                /**
                 * MessageQueueSelector 是保证消息顺序消费的关键
                 * 同一个订单的消息将被发送到同一个队列
                 */
                SendResult sendResult = producer.send(msg, new MessageQueueSelector() {

                    @Override
                    public MessageQueue select(List<MessageQueue> mgs, Message msg,
                                               Object arg) {
                        // TODO Auto-generated method stub
                        Integer id = (Integer) arg;
                        int index = id % mgs.size();

                        return mgs.get(index);
                    }
                }, orderId);
                System.out.println(sendResult);
            }
        }
        producer.shutdown();
    }
}
