package com.wei.demo.rocketmq.integrated.event;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wei.demo.rocketmq.integrated.mq.Consumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @Author: weiwenfeng
 * @Date: 2018/11/5
 */
@Component
public class HandlerExecutor implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void initExecutor(){
        Consumer consumer = applicationContext.getBean(Consumer.class);
        Map<String,Object> eventMap = applicationContext.getBeansWithAnnotation(EventHandler.class);
        if (null != eventMap){
            for (Map.Entry<String,Object> entry : eventMap.entrySet()){
                IEventHandler eventHandler = (IEventHandler)entry.getValue();
                String[] tables = eventHandler.getClass().getAnnotation(EventHandler.class).tables();
                for (String table : tables){
                    consumer.subscribe(table,null,
                            String.format("%s-%s","deault-test-group",eventHandler.getClass().getSimpleName()),
                            createMessageListenerOrderly(eventHandler));
                }
            }
        }
    }

    /**
     *  创建顺序消费监听器
     * @param eventHandler
     * @return
     */
    public MessageListenerOrderly createMessageListenerOrderly(IEventHandler eventHandler){
        return new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                for (MessageExt ext : msgs){
                    JSONObject jsonObject = JSONObject.parseObject(new String(ext.getBody()));
                    String tableName = jsonObject.getString("tableName");
                    String event = jsonObject.getString("event");
                    JSONArray jsonArray = jsonObject.getJSONArray("rowList");
                    if (event.equals("insert")){
                        eventHandler.insert(tableName,jsonArray);
                    }else if (event.equals("update")){
                        eventHandler.update(tableName,jsonArray);
                    }else if (event.equals("delete")){
                        eventHandler.delete(tableName,jsonArray);
                    }
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        };
    }
}
