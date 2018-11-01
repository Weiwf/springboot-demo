package com.wei.demo.rocketmq.rocketmq.officaldemo.orderexample;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @Author: weiwenfeng
 * @Date: 2018/11/1
 */
public class Producer {
    public static void main(String[] args) throws IOException {
        try {
            DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
            producer.setNamesrvAddr("127.0.0.1:9876");
            producer.start();

            String[] tags = new String[] { "TagA", "TagC", "TagD" };

            // 订单列表
            List<OrderDemo> orderList =  new Producer().buildOrders();

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = sdf.format(date);
            for (int i = 0; i < 10; i++) {
                // 加个时间后缀
                String body = dateStr + " Hello RocketMQ " + orderList.get(i);
                Message msg = new Message("TopicTestjjj", tags[i % tags.length], "KEY" + i, body.getBytes());

                SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        Long id = (Long) arg;
                        long index = id % mqs.size();
                        System.out.println("---------- id : " + id);
                        System.out.println("---------- index : " + index);
                        return mqs.get((int)index);
                    }
                }, orderList.get(i).getOrderId());//订单id

                System.out.println(sendResult + ", body:" + body);
            }

            producer.shutdown();

        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.in.read();
    }

    /**
     * 生成模拟订单数据
     */
    private List<OrderDemo> buildOrders() {
        List<OrderDemo> orderList = new ArrayList<OrderDemo>();

        // 订单1
        OrderDemo orderDemo = new OrderDemo();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("1.创建");
        orderList.add(orderDemo);

        orderDemo = new OrderDemo();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("2.付款");
        orderList.add(orderDemo);

        orderDemo = new OrderDemo();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("3.推送");
        orderList.add(orderDemo);

        orderDemo = new OrderDemo();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("4.完成");
        orderList.add(orderDemo);

        //订单2
        orderDemo = new OrderDemo();
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("1.创建");
        orderList.add(orderDemo);

        orderDemo = new OrderDemo();
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("2.付款");
        orderList.add(orderDemo);

        orderDemo = new OrderDemo();
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("3.完成");
        orderList.add(orderDemo);

        // 订单3
        orderDemo = new OrderDemo();
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("1.创建");
        orderList.add(orderDemo);

        orderDemo = new OrderDemo();
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("2.付款");
        orderList.add(orderDemo);

        orderDemo = new OrderDemo();
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("3.完成");
        orderList.add(orderDemo);

        // 打乱订单
        Collections.shuffle(orderList);
        return orderList;
    }
}
