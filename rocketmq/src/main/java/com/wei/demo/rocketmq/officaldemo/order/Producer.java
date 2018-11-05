package com.wei.demo.rocketmq.officaldemo.order;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: weiwenfeng
 * @Date: 2018/11/1
 */
public class Producer {
    public static void main(String[] args) throws IOException {
        try {
            DefaultMQProducer producer = new DefaultMQProducer("default-group");
            producer.setNamesrvAddr("127.0.0.1:9876");
            producer.start();

            List<OrderDemo> orderDemos = new Producer().buildOrders();
            for (int i = 0; i < orderDemos.size(); i++) {
                String body = orderDemos.get(i).toString();
                Message message = new Message("order-topic", null, null,body.getBytes());
                SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        long orderId = (long) arg;
                        int index = (int) orderId % mqs.size();
                        return mqs.get(index);
                    }
                }, orderDemos.get(i).getOrderId());
                System.out.println(sendResult + ",body:" + body);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 生成模拟订单数据
     */
    private List<OrderDemo> buildOrders() {
        List<OrderDemo> orderList = new ArrayList<OrderDemo>();

        OrderDemo orderDemo = new OrderDemo();
        orderDemo.setOrderId(1001);
        orderDemo.setDesc("1001订单创建");
        orderList.add(orderDemo);

        orderDemo = new OrderDemo();
        orderDemo.setOrderId(1002);
        orderDemo.setDesc("1002订单创建");
        orderList.add(orderDemo);

        orderDemo = new OrderDemo();
        orderDemo.setOrderId(1001);
        orderDemo.setDesc("1001订单发送");
        orderList.add(orderDemo);

        orderDemo = new OrderDemo();
        orderDemo.setOrderId(1001);
        ;
        orderDemo.setDesc("1001订单完成");
        orderList.add(orderDemo);

        orderDemo = new OrderDemo();
        orderDemo.setOrderId(1002);
        orderDemo.setDesc("1002订单发送");
        orderList.add(orderDemo);

        orderDemo = new OrderDemo();
        orderDemo.setOrderId(1002);
        ;
        orderDemo.setDesc("1002订单完成");
        orderList.add(orderDemo);

        return orderList;
    }
}
